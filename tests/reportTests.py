import unittest
import consts
import auth
import requests
import functools as ft
class TestReportAPI(unittest.TestCase):
    dep1 = {}
    dep2 = {}
    wh1 = {}
    wh2 = {}
    fs1 = {}
    fs2 = {}
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




        self.wh2 = {
            "departmentId" : {"id":self.dep2["id"]["id"]},
            "name": "ProstrelSNoja"
        }

        self.fs2 = {
            "departmentId" : {"id":self.dep2["id"]["id"]},
            "name": "HalalBotwa"
        }

        create_r_2 = requests.post(consts.API_WAREHOUSE_PREFIX + "create", json = self.wh2, headers=self.headers)
        self.assertEqual(create_r_2.status_code, 201)
        self.wh2.update({"id": create_r_2.json()})


        create_r_2 = requests.post(consts.API_FACTORYSITE_PREFIX + "create", json = self.fs2, headers=self.headers)
        self.assertEqual(create_r_2.status_code, 201)
        self.fs2.update({"id": create_r_2.json(), "suppliers": []})



        print("update_supply_1: ")
        update_supply_payload = {
            "id" : self.fs1["id"],
            "suppliers": [self.wh1["id"]]
        }
        self.fs1.update({"suppliers": [self.wh1["id"]]})
        update_r = requests.put(consts.API_FACTORYSITE_PREFIX + "updateSupply", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)
        

        print("update_supply_2: ")
        update_supply_payload = {
            "id" : self.fs2["id"],
            "suppliers": [self.wh2["id"]]
        }
        self.fs2.update({"suppliers": [self.wh2["id"]]})
        update_r = requests.put(consts.API_FACTORYSITE_PREFIX + "updateSupply", json = update_supply_payload, headers=self.headers)
        self.assertEqual(update_r.status_code, 204)

        
        
        
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
    
        report_inventarisation = {
            "type":"inventarisation",
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2}
        }

        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_inventarisation, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_inventarisation: ")
        print("     " , create_report1.json())
        report_inventarisation.update({"id": create_report1.json()})





        report_shipment = {
            "type":"shipment",
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2}
        }

        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_shipment, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_shipment: ")
        print("     " , create_report1.json())
        report_shipment.update({"id": create_report1.json()})

        report_replenish = {
            "type":"replenishment",
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2}
        }

        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_replenish, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_replenish: ")
        print("     " , create_report1.json())
        report_replenish.update({"id": create_report1.json()})





        report_supply_req = {
            "type":"supplyrequirement",
            "factorySiteId": self.fs1["id"],
            "targetWareHouseIds" : [self.wh1["id"]],
            "items": {self.item2["id"]["id"]: 2}
        }
        print(report_supply_req)
        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_supply_req, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_supplyrequirement: ")
        print("     " , create_report1.json())
        report_supply_req.update({"id": create_report1.json()})

        report_work_shift = {
            "type":"workshift",
            "factorySiteId": self.fs1["id"],
            "targetWareHouseIds" : [self.wh1["id"]],
            "produced": {self.product1["id"]["id"] : 1},
            "losses": {self.item2["id"]["id"]: 2},
            'remains': {}
        }
        print(report_work_shift)
        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_work_shift, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_workshift: ")
        print("     " , create_report1.json())
        report_work_shift.update({"id": create_report1.json()})
        
        
        report_wsreplenish = {
            "type":"workshiftreplenishment",
            "workShiftReportId" : report_work_shift["id"],
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2},
            'unclaimedRemains': {}
        }
        print(report_wsreplenish)
        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_wsreplenish, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_workshift_replenish: ")
        print("     " , create_report1.json())
        report_wsreplenish.update({"id": create_report1.json()})
    
        report_release = {
            "type":"release",
            "supplyReqReportId" : report_supply_req["id"],
            "wareHouseId": self.wh1["id"],
            "items": {self.item2["id"]["id"]: 2}
        }
        comp = lambda res: res["id"]["id"]
        create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_release, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_release: ")
        print("     " , create_report1.json())
        report_release.update({"id": create_report1.json()})
        
        fetch_one_1_json=[report_inventarisation]
        print("fetch_one: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"id": report_inventarisation["id"]["id"]}, headers=self.headers)
        self.assertEqual(fetch_r.status_code, 200)
        print("     " , sorted(fetch_r.json(), key=comp))
        print("excepted:" , sorted(fetch_one_1_json, key=comp))
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_one_1_json, key=comp))

        fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_supply_req, report_work_shift, report_wsreplenish]
        print("fetch_generics: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "locationSpecificId" : self.dep1["id"]["id"]}, headers=self.headers)
        print("     " , sorted(fetch_r.json(), key=comp))
        print("excepted:" , sorted(fetch_1_json, key=comp))
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
        print("fetch_warehouse_generic: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "warehouse_generic", "locationSpecificId" : self.wh1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json = [report_supply_req, report_work_shift]
        print("fetch_factorysite_generic: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "factorysite_generic", "locationSpecificId" : self.fs1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json=[report_inventarisation]
        print("fetch_inventarisation: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "inventarisation", "locationSpecificId" : self.wh1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json=[report_work_shift]
        print("fetch_report_work_shift: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "workshift", "locationSpecificId" : self.fs1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json=[report_release]
        print("fetch_release: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "release", "locationSpecificId" : self.wh1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        print("excepted:" , fetch_1_json)
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

        fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_supply_req, report_work_shift, report_wsreplenish]
        print("fetch_generics_limit 5: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 5 , "locationSpecificId" : self.dep1["id"]["id"]}, headers=self.headers)
        print("     " , sorted(fetch_r.json(), key=comp))

        self.assertEqual(len(fetch_r.json()), 5)
        self.assertEqual(fetch_r.status_code, 200)
        #self.assertEqual(reduce(True, fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))


        fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
        print("fetch_warehouse_generic_limit 3: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic", "locationSpecificId" : self.wh1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(len(fetch_r.json()), 3)
        self.assertEqual(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True), True)

        key1 = fetch_r.json()[-1]["id"]["id"]
        fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
        print("fetch_warehouse_generic_limit_3_key: " + key1)
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic", "key": key1, "locationSpecificId" : self.wh1["id"]["id"]}, headers=self.headers)
        print("     " , fetch_r.json())
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(len(fetch_r.json()), 2)
        self.assertEqual(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True), True)











        report_inventarisation2 = {
            "type":"inventarisation",
            "wareHouseId": self.wh2["id"],
            "items": {self.item2["id"]["id"]: 2}
        }

        create_report2 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_inventarisation2, headers=self.headers)
        self.assertEqual(create_report1.status_code, 201)
        print("create_inventarisation: ")
        print("     " , create_report2.json())
        report_inventarisation2.update({"id": create_report2.json()})


        fetch_2_json = [report_inventarisation2]
        print("fetch_generics2: ")
        fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "locationSpecificId" : self.dep2["id"]["id"]}, headers=self.headers)
        print("     " , sorted(fetch_r.json(), key=comp))
        print("excepted:" , sorted(fetch_2_json, key=comp))
        self.assertEqual(fetch_r.status_code, 200)
        self.assertEqual(sorted(fetch_r.json(), key=comp), sorted(fetch_2_json, key=comp))

        
if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TestReportAPI)
    unittest.TextTestRunner(verbosity=2).run(suite)
