import unittest
import consts
import auth
import requests
class TestUserGroupAPI(unittest.TestCase):
    headers = {}
    def setUp(self):
        #init_something()
        pass
        
    def tearDown(self):
        #teardown_something()
        pass
        
    def test_complex(self):

        self.headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
        group1 = {
            "name": "SalamiSeleven"
        }
        user1 = {
            "email" : "userEzikVTUMoney@abc.ru",
            "password" : "RedCorals1111",
            "name": "EzikVTUMoney"
        }
        create_group_r_1 = requests.post(consts.API_USERGROUP_PREFIX + "addGroup", json = group1, headers=self.headers)
        self.assertEqual(create_group_r_1.status_code, 201)
        print("create_group_r_1: ")
        print("     " , create_group_r_1.json())
        group1.update({"id": create_group_r_1.json()})
        
        
        fetch_1_json=[group1]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchGroup", headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        
        create_user_r_1 = requests.post(consts.API_USERGROUP_PREFIX + "addUser", json = user1, headers=self.headers)
        self.assertEqual(create_user_r_1.status_code, 201)
        print("create_user_r_1: ")
        print("     " , create_user_r_1.json())
        user1.update({"id": create_user_r_1.json()})
        
        fetch_1_json=[{'id': create_user_r_1.json(), 'name': 'EzikVTUMoney', 'groups': []}]
        print("fetch_1: ")
        fetch_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", headers=self.headers)
        print("     " , fetch_r.json())
        print("     " , list(filter(lambda u: u['name'] == user1['name'], fetch_r.json())))
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(list(filter(lambda u: u['name'] == user1['name'], fetch_r.json())), fetch_1_json)
        
        print("update_1: ")
        group1["name"] = "ShanhaiSakura"
        update_r = requests.put(consts.API_USERGROUP_PREFIX + "updateGroup", json = group1, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        fetch_1_json=[group1]
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchGroup", headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(fetch_r.json(), fetch_1_json)
        
        print("update_2: ")
        user1["name"] = "ShanhaiSakura"
        update_r = requests.put(consts.API_USERGROUP_PREFIX + "updateUser", json = user1, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        
        fetch_1_json=[{'id': user1["id"], 'name': user1["name"], 'groups': []}]
        print("fetch_2: ")
        fetch_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", headers=self.headers)
        print("     " , fetch_r.json())
        print("     " , list(filter(lambda u: u['name'] == user1['name'], fetch_r.json())))
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(list(filter(lambda u: u['name'] == user1['name'], fetch_r.json())), fetch_1_json)
        
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchGroup", params = {"id": group1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        print("     excepted:", [group1])
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_one_r.json(), [group1])
        
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_one_r.json(), [{'id': user1["id"], 'name': user1["name"], 'groups': []}])
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_USERGROUP_PREFIX + "removeGroup", params = {"id": group1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_USERGROUP_PREFIX + "removeUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
    def test_include_user_to_group(self):

        self.headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
        group1 = {
            "name": "SalamiSeleven"
        }
        user1 = {
            "email" : "userEzikVTUMoney@abc.ru",
            "password" : "RedCorals1111",
            "name": "EzikVTUMoney"
        }
        create_group_r_1 = requests.post(consts.API_USERGROUP_PREFIX + "addGroup", json = group1, headers=self.headers)
        self.assertEqual(create_group_r_1.status_code, 201)
        print("create_group_r_1: ")
        print("     " , create_group_r_1.json())
        group1.update({"id": create_group_r_1.json()})
        
        create_user_r_1 = requests.post(consts.API_USERGROUP_PREFIX + "addUser", json = user1, headers=self.headers)
        self.assertEqual(create_user_r_1.status_code, 201)
        print("create_user_r_1: ")
        print("     " , create_user_r_1.json())
        user1.update({"id": create_user_r_1.json()})
      
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_one_r.json(), [{'id': user1["id"], 'name': user1["name"], 'groups': []}])
        
        
        print("update_group users_1: ")
        update_supply_payload = {
            "id" : group1["id"],
            "users": [user1["id"]]
        }
        #fs1.update({"suppliers": [self.wh1["id"]]})
        update_r = requests.put(consts.API_USERGROUP_PREFIX + "includeUsers", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("fetch_one_1: ")
        fetch_one_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_one_r.json(), [{'id': user1["id"], 'name': user1["name"], 'groups': [group1["id"]]}])
        
        print("update_group users_2: ")
        update_supply_payload = {
            "id" : group1["id"],
            "users": [user1["id"]],
            "exclude": True
        }
        #fs1.update({"suppliers": [self.wh1["id"]]})
        update_r = requests.put(consts.API_USERGROUP_PREFIX + "includeUsers", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        
        print("fetch_one_2: ")
        fetch_one_r = requests.get(consts.API_USERGROUP_PREFIX + "fetchUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        print("     ", fetch_one_r.json())
        self.assertEqual(fetch_one_r.status_code, 200)
        self.assertEqual(fetch_one_r.json(), [{'id': user1["id"], 'name': user1["name"], 'groups': []}])
        
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_USERGROUP_PREFIX + "removeGroup", params = {"id": group1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
        print("delete_2: ")
        delete_r = requests.delete(consts.API_USERGROUP_PREFIX + "removeUser", params = {"id": user1["id"]["id"]}, headers=self.headers)
        self.assertEqual(delete_r.status_code, 204)
        
       
        
        
        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestUserGroupAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
