import unittest
import consts
import auth
import requests
import string
import random

def generate_random_string(length):
    # Выборка из всех букв и цифр
    characters = string.ascii_letters + string.digits
    # Генерация случайной строки из выбранных символов
    random_string = ''.join(random.choice(characters) for i in range(length))
    return random_string

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
            "name": "SalamiSelevenAccess"
        }
        dep2 = {
            "name": "EzikVTYMoneyAccess"
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

        random_str = generate_random_string(5)
        print("Токен User/Group:", random_str)
        group1 = {
            "name": "EzikVTUMoneyGroup$" + random_str + ""
        }
        user1 = {
            "email" : "userEzikVTUMoney" + random_str + "@abc.ru",
            "password" : "RedCorals1111",
            "name": "EzikVTUMoney" + random_str
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

        print("update_group users_1: ")
        update_supply_payload = {
            "id" : group1["id"],
            "users": [user1["id"]]
        }
        #fs1.update({"suppliers": [self.wh1["id"]]})
        update_r = requests.put(consts.API_USERGROUP_PREFIX + "setUsers", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)

        print(consts.API_ACCESS_PREFIX + "modifyAccess")
        access_r = requests.post(consts.API_ACCESS_PREFIX + "modifyAccess", json = {"resourceId": dep1["id"]["id"], "resourceType": "department", "accessorId": user1["id"]["id"], "permissions": ["READ"]}, headers=self.headers)
        self.assertEqual(access_r.status_code, 200)




if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestDepartmentAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
