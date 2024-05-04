import unittest
import consts
import auth
import requests
class TestReportAPI(unittest.TestCase):
    dep1 = {}
    dep2 = {}
    wh1 = {}
    item1={}
    item2={}
    product1={}
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
        
        self.fs1 = {
            "departmentId" : {"id":self.dep1["id"]["id"]},
            "name": "BanzaiMazai"
        }

        create_r_1 = requests.post(consts.API_WAREHOUSE_PREFIX + "create", json = self.wh1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        self.wh1.update({"id": create_r_1.json()})
        
        
        create_r_1 = requests.post(consts.API_FACTORYSITE_PREFIX + "create", json = self.fs1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        self.fs1.update({"id": create_r_1.json(), "suppliers": []})
        
        
        
        
        self.item1 = {
            "name": "SalamiSeleven",
            "units": "units"
        }
        self.item2 = {
            "name": "SalamiPart",
            "units": "units"
        }
        
        create_item_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = self.item1, headers=self.headers)
        self.assertEqual(create_item_r_1.status_code, 201)
        self.item1.update({"id": create_item_r_1.json()})
        
        
        create_item_r_2 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = self.item2, headers=self.headers)
        self.assertEqual(create_item_r_2.status_code, 201)
        self.item2.update({"id": create_item_r_2.json()})
        
        product1 = {
            "producedItemId": item1["id"],
            "requirements": {item2["id"]["id"]: 2}
        }
        
        self.create_product_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addProduct", json = product1, headers=self.headers)
        self.assertEqual(create_product_r_1.status_code, 201)
        self.product1.update({"id": create_product_r_1.json()})
        
    def tearDown(self):
    
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep2["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        delete_r = requests.delete(consts.API_WAREHOUSE_PREFIX + "delete", params = {"id": self.wh1["id"]["id"]}, headers = self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        delete_r = requests.delete(consts.API_FACTORYSITE_PREFIX + "delete", params = {"id": self.fs1["id"]["id"]}, headers = self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
    def test_complex(self):
        
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
        
        
        print("fetch_3: ")
        fetch_r = requests.get(consts.API_FACTORYSITE_PREFIX + "fetch", headers = self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [])
        
  
    
    
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestReportAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
