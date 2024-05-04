import unittest
import consts
import auth
import requests
class TestRegistriesAPI(unittest.TestCase):
    headers = {}
    def setUp(self):
        #init_something()
        pass
        
    def tearDown(self):
        #teardown_something()
        pass
        
    def test_complex(self):

        self.headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
        item1 = {
            "name": "SalamiSeleven",
            "units": "units"
        }
        item2 = {
            "name": "SalamiPart",
            "units": "units"
        }
        create_item_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = item1, headers=self.headers)
        self.assertEqual(create_item_r_1.status_code, 201)
        print("create_item_r_1: ")
        print("     " , create_item_r_1.json())
        item1.update({"id": create_item_r_1.json()})
        
        
        create_item_r_2 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = item2, headers=self.headers)
        self.assertEqual(create_item_r_2.status_code, 201)
        print("create_item_r_2: ")
        print("     " , create_item_r_2.json())
        item2.update({"id": create_item_r_2.json()})
        
        
        fetch_1_json=[item1,item2]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_REGISTRIES_PREFIX + "fetchItem", headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        
        product1 = {
            "producedItemId": item1["id"],
            "requirements": {item2["id"]["id"]: 2}
        }
        
        create_product_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addProduct", json = product1, headers=self.headers)
        self.assertEqual(create_product_r_1.status_code, 201)
        print("create_product_r_1: ")
        print("     " , create_product_r_1.json())
        product1.update({"id": create_product_r_1.json()})
        
        fetch_1_json=[product1]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_REGISTRIES_PREFIX + "fetchProduct", headers=self.headers)
        print("     " , fetch_r.json())
 
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        item1.update({"name": "SalamiPart2"})
        update_item_r_1 = requests.put(consts.API_REGISTRIES_PREFIX + "updateItem", json = item1, headers=self.headers)
        self.assertEqual(update_item_r_1.status_code, 204)
        
        
        product1.update({"requirements": {item2["id"]["id"]: 10}})
        update_product_r_1 = requests.put(consts.API_REGISTRIES_PREFIX + "updateProduct", json = product1, headers=self.headers)
        self.assertEqual(update_product_r_1.status_code, 204)
        
        
       
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestRegistriesAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
