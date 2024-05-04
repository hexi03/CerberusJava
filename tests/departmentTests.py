import unittest
import consts
import auth
import requests
class TestDepartmentAPI(unittest.TestCase):
    headers = {}
    def setUp(self):
        #init_something()
        pass
        
    def tearDown(self):
        #teardown_something()
        pass
        
    def test_complex(self):

        self.headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
        dep1 = {
            "name": "SalamiSeleven"
        }
        dep2 = {
            "name": "EzikVTYMoney"
        }
        create_r_1 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = dep1, headers=self.headers)
        self.assertEqual(create_r_1.status_code, 201)
        print("create_r_1: ")
        print("     " , create_r_1.json())
        dep1.update({"id": create_r_1.json(),'wareHouses': [], 'factorySites': []})
        
        create_r_2 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = dep2, headers=self.headers)
        self.assertEqual(create_r_2.status_code, 201)
        print("create_r_2: ")
        print("     " , create_r_2.json())
        dep2.update({"id": create_r_2.json(),'wareHouses': [], 'factorySites': []})
        
        fetch_1_json=[dep1,dep2]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_DEPARTMENT_PREFIX + "fetch", headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        print("update_1: ")
        dep1["name"] = "ShanhaiSakura"
        update_r = requests.put(consts.API_DEPARTMENT_PREFIX + "update", json = dep1, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": dep2["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_DEPARTMENT_PREFIX + "fetch", headers=self.headers)
        print("     ", fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [dep1])
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_DEPARTMENT_PREFIX + "fetch", params = {"id": dep1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_r.json(), [dep1])
        
        delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": dep1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestDepartmentAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
