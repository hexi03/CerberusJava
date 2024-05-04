import unittest
import consts
import auth
import requests
class TestFactorySiteAPI(unittest.TestCase):
    dep1 = {}
    dep2 = {}
    wh1 = {}
    headers = {}
    def setUp(self):
        self.headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
        self.dep1 = {
            "name": "SalamiSeleven"
        }
        self.dep2 = {
            "name": "EzikVTYMoney"
        }
        create_r_1 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = self.dep1, headers=self.headers)
        self.dep1.update({"id": create_r_1.json(),'wareHouses': [], 'factorySites': []})
        self.assertEqual(create_r_1.status_code, 201)
        create_r_2 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = self.dep2, headers=self.headers)
        self.assertEqual(create_r_2.status_code, 201)
        self.dep2.update({"id": create_r_2.json(),'wareHouses': [], 'factorySites': []})
        
        self.wh1 = {
            "departmentId" : {"id":self.dep1["id"]["id"]},
            "name": "SalamiSeleven"
        }

        create_r_1 = requests.post(consts.API_WAREHOUSE_PREFIX + "create", json = self.wh1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        self.wh1.update({"id": create_r_1.json()})
        
    def tearDown(self):
    
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep2["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        delete_r = requests.delete(consts.API_WAREHOUSE_PREFIX + "delete", params = {"id": self.wh1["id"]["id"]}, headers = self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        
    def test_complex(self):
        fs1 = {
            "departmentId" : {"id":self.dep1["id"]["id"]},
            "name": "SalamiSeleven"
        }

        create_r_1 = requests.post(consts.API_FACTORYSITE_PREFIX + "create", json = fs1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        print("create_r_1: ")
        print("     " , create_r_1.json())
        fs1.update({"id": create_r_1.json(), "suppliers": []})
        
        fetch_1_json=[fs1]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        print("update_1: ")
        fs1["name"] = "ShanhaiSakura"
        update_r = requests.put(consts.API_FACTORYSITE_PREFIX + "update", json = fs1, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [fs1])
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", params = {"id": fs1["id"]["id"]}, headers = self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [fs1])
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_FACTORYSITE_PREFIX + "delete", params = {"id": fs1["id"]["id"]}, headers = self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        print("fetch_3: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [])
        
    def test_supply(self):
        fs1 = {
            "departmentId" : {"id":self.dep1["id"]["id"]},
            "name": "SalamiSeleven"
        }

        create_r_1 = requests.post(consts.API_FACTORYSITE_PREFIX + "create", json = fs1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        print("create_r_1: ")
        print("     " , create_r_1.json())
        fs1.update({"id": create_r_1.json(), "suppliers": []})
        
        fetch_1_json=[fs1]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        print("update_supply_1: ")
        update_supply_payload = {
            "id" : fs1["id"],
            "suppliers": [self.wh1["id"]]
        }
        fs1.update({"suppliers": [self.wh1["id"]]})
        update_r = requests.put(consts.API_FACTORYSITE_PREFIX + "updateSupply", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [fs1])
        
        
        
        print("update_supply_2: ")
        update_supply_payload = {
            "id" : fs1["id"],
            "suppliers": []
        }
        fs1.update({"suppliers": []})
        update_r = requests.put(consts.API_FACTORYSITE_PREFIX + "updateSupply", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [fs1])
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_FACTORYSITE_PREFIX + "delete", params = {"id": fs1["id"]["id"]}, headers = self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
    
    
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestFactorySiteAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
