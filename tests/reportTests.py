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
        
        self.product1 = {
            "producedItemId": self.item1["id"],
            "requirements": {self.item2["id"]["id"]: 2}
        }
        
        create_product_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addProduct", json = self.product1, headers=self.headers)
        self.assertEqual(create_product_r_1.status_code, 201)
        self.product1.update({"id": create_product_r_1.json()})
        
    def tearDown(self):
        pass
        #delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep1["id"]["id"]}, headers=self.headers)
        #self.assertEqual(delete_r.status_code, 204)
        #delete_r = requests.delete(consts.API_DEPARTMENT_PREFIX + "delete", params = {"id": self.dep2["id"]["id"]}, headers=self.headers)
        #self.assertEqual(delete_r.status_code, 204)
        
        #delete_r = requests.delete(consts.API_WAREHOUSE_PREFIX + "delete", params = {"id": self.wh1["id"]["id"]}, headers = self.headers)
        #self.assertEqual(delete_r.status_code, 204)
        
        #delete_r = requests.delete(consts.API_FACTORYSITE_PREFIX + "delete", params = {"id": self.fs1["id"]["id"]}, headers = self.headers)
        #self.assertEqual(delete_r.status_code, 204)
        
    def test_complex(self):
    
        report1 = {
            "type":"inventarisation",
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2}
        }
        print(report1)
        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report1, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_inventarisation: ")
        print("     " , create_report1.json())
        report1.update({"id": create_report1.json()})
        
        fetch_one_1_json=[report1]
        print("fetch_one: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"id": report1["id"]["id"]}, headers=self.headers)
        self.assertEqual(fetch_r.status_code, 200)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_one_1_json)
        self.assertEqual(fetch_r.json(), fetch_one_1_json)
        
        fetch_1_json=[report1]
        print("fetch_generics: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)

        fetch_1_json=[report1]
        print("fetch_warehouse_generic: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "warehouse_generic"}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)

        fetch_1_json=[report1]
        print("fetch_inventarisation: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "inventarisation"}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)

        fetch_1_json=[]
        print("fetch_release: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "release"}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        
        
  
    
    
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestReportAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
