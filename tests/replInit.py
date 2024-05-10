import unittest
import consts
import auth
import requests
import functools as ft
headers = {}
headers.update({"Authorization": "Bearer "+auth.get_auth_token()})










headers.update({"Authorization": "Bearer "+auth.get_auth_token()})
dep1 = {
    "name": "SalamiSeleven"
}
dep2 = {
    "name": "EzikVTYMoney"
}
create_r_1 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = dep1, headers=headers)
dep1.update({"id": create_r_1.json(),'wareHouses': [], 'factorySites': []})
print(create_r_1.status_code, 201)
create_r_2 = requests.post(consts.API_DEPARTMENT_PREFIX + "create", json = dep2, headers=headers)
print(create_r_2.status_code, 201)
dep2.update({"id": create_r_2.json(),'wareHouses': [], 'factorySites': []})

wh1 = {
    "departmentId" : {"id":dep1["id"]["id"]},
    "name": "SalamiSeleven"
}

fs1 = {
    "departmentId" : {"id":dep1["id"]["id"]},
    "name": "BanzaiMazai"
}

create_r_1 = requests.post(consts.API_WAREHOUSE_PREFIX + "create", json = wh1, headers=headers)
print(create_r_1.status_code, 201)
wh1.update({"id": create_r_1.json()})


create_r_1 = requests.post(consts.API_FACTORYSITE_PREFIX + "create", json = fs1, headers=headers)
print(create_r_1.status_code, 201)
fs1.update({"id": create_r_1.json(), "suppliers": []})




item1 = {
    "name": "SalamiSeleven",
    "units": "units"
}
item2 = {
    "name": "SalamiPart",
    "units": "units"
}

create_item_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = item1, headers=headers)
print(create_item_r_1.status_code, 201)
item1.update({"id": create_item_r_1.json()})


create_item_r_2 = requests.post(consts.API_REGISTRIES_PREFIX + "addItem", json = item2, headers=headers)
print(create_item_r_2.status_code, 201)
item2.update({"id": create_item_r_2.json()})

product1 = {
    "producedItemId": item1["id"],
    "requirements": {item2["id"]["id"]: 2}
}

create_product_r_1 = requests.post(consts.API_REGISTRIES_PREFIX + "addProduct", json = product1, headers=headers)
print(create_product_r_1.status_code, 201)
product1.update({"id": create_product_r_1.json()})



report_inventarisation = {
    "type":"inventarisation",
    "wareHouseId": wh1["id"],
    "items": {item2["id"]["id"]: 2}
}

create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_inventarisation, headers=headers)
print(create_report1.status_code, 201)
print("create_inventarisation: ")
print("     " , create_report1.json())
report_inventarisation.update({"id": create_report1.json()})





report_shipment = {
    "type":"shipment",
    "wareHouseId": wh1["id"],
    "items": {item2["id"]["id"]: 2}
}

create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_shipment, headers=headers)
print(create_report1.status_code, 201)
print("create_shipment: ")
print("     " , create_report1.json())
report_shipment.update({"id": create_report1.json()})

report_replenish = {
    "type":"replenishment",
    "wareHouseId": wh1["id"],
    "items": {item2["id"]["id"]: 2}
}

create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_replenish, headers=headers)
print(create_report1.status_code, 201)
print("create_replenish: ")
print("     " , create_report1.json())
report_replenish.update({"id": create_report1.json()})





report_supply_req = {
    "type":"supplyrequirement",
    "factorySiteId": fs1["id"],
    "targetWareHouseId" : wh1["id"],
    "items": {item2["id"]["id"]: 2}
}
print(report_supply_req)
create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_supply_req, headers=headers)
print(create_report1.status_code, 201)
print("create_supplyrequirement: ")
print("     " , create_report1.json())
report_supply_req.update({"id": create_report1.json()})

report_work_shift = {
    "type":"workshift",
    "factorySiteId": fs1["id"],
    "targetWareHouseId" : wh1["id"],
    "produced": {product1["id"]["id"] : 1},
    "losses": {item2["id"]["id"]: 2},
    'remains': {}
}
print(report_work_shift)
create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_work_shift, headers=headers)
print(create_report1.status_code, 201)
print("create_workshift: ")
print("     " , create_report1.json())
report_work_shift.update({"id": create_report1.json()})


report_wsreplenish = {
    "type":"workshiftreplenishment",
    "workShiftReportId" : report_work_shift["id"],
    "wareHouseId": wh1["id"],
    "items": {item2["id"]["id"]: 2},
    'unclaimedRemains': {}
}
print(report_wsreplenish)
create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_wsreplenish, headers=headers)
print(create_report1.status_code, 201)
print("create_workshift_replenish: ")
print("     " , create_report1.json())
report_wsreplenish.update({"id": create_report1.json()})

report_release = {
    "type":"release",
    "supplyReqReportId" : report_supply_req["id"],
    "wareHouseId": wh1["id"],
    "items": {item2["id"]["id"]: 2}
}
comp = lambda res: res["id"]["id"]
create_report1 = requests.post(consts.API_REPORT_PREFIX + "append", json = report_release, headers=headers)
print(create_report1.status_code, 201)
print("create_release: ")
print("     " , create_report1.json())
report_release.update({"id": create_report1.json()})

fetch_one_1_json=[report_inventarisation]
print("fetch_one: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"id": report_inventarisation["id"]["id"]}, headers=headers)
print(fetch_r.status_code, 200)
print("     " , sorted(fetch_r.json(), key=comp))
print("excepted:" , sorted(fetch_one_1_json, key=comp))
print(sorted(fetch_r.json(), key=comp), sorted(fetch_one_1_json, key=comp))

fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_supply_req, report_work_shift, report_wsreplenish]
print("fetch_generics: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10}, headers=headers)
print("     " , sorted(fetch_r.json(), key=comp))
print("excepted:" , sorted(fetch_1_json, key=comp))
print(fetch_r.status_code, 200)
print(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
print("fetch_warehouse_generic: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "warehouse_generic"}, headers=headers)
print("     " , fetch_r.json())
print("excepted:" , fetch_1_json)
print(fetch_r.status_code, 200)
print(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

fetch_1_json = [report_supply_req, report_work_shift]
print("fetch_factorysite_generic: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "factorysite_generic"}, headers=headers)
print("     " , fetch_r.json())
print("excepted:" , fetch_1_json)
print(fetch_r.status_code, 200)
print(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

fetch_1_json=[report_inventarisation]
print("fetch_inventarisation: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "inventarisation"}, headers=headers)
print("     " , fetch_r.json())
print("excepted:" , fetch_1_json)
print(fetch_r.status_code, 200)
print(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

fetch_1_json=[report_release]
print("fetch_release: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 10, "typeCriteria": "release"}, headers=headers)
print("     " , fetch_r.json())
print("excepted:" , fetch_1_json)
print(fetch_r.status_code, 200)
print(sorted(fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))

fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_supply_req, report_work_shift, report_wsreplenish]
print("fetch_generics_limit 5: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 5}, headers=headers)
print("     " , sorted(fetch_r.json(), key=comp))

print(len(fetch_r.json()), 5)
print(fetch_r.status_code, 200)
#print(reduce(True, fetch_r.json(), key=comp), sorted(fetch_1_json, key=comp))


fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
print("fetch_warehouse_generic_limit 3: ")
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic"}, headers=headers)
print("     " , fetch_r.json())
print(fetch_r.status_code, 200)
print(len(fetch_r.json()), 3)
print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True), True)

key1 = fetch_r.json()[-1]["id"]["id"]
fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
print("fetch_warehouse_generic_limit_3_key: " + key1)
fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic", "key": key1}, headers=headers)
print("     " , fetch_r.json())
print(fetch_r.status_code, 200)
print(len(fetch_r.json()), 2)
print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True), True)



def req():
    fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
    print("fetch_warehouse_generic_limit 3: ")
    fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic"}, headers=headers)
    print("     " , fetch_r.json())
    print(fetch_r.status_code, 200)
    print(len(fetch_r.json()), 3)
    print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True) == True)
    for rep in fetch_r.json():
        print(rep["type"] + "    " + rep["id"]["id"])

    key1 = fetch_r.json()[-1]["id"]["id"]
    fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
    print("fetch_warehouse_generic_limit_3_key: " + key1)
    fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "typeCriteria": "warehouse_generic", "key": key1}, headers=headers)
    print("     " , fetch_r.json())
    print(fetch_r.status_code, 200)
    print(len(fetch_r.json()), 2)
    print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True) == True)
    fetch_r.json()
    for rep in fetch_r.json():
        print(rep["type"] + "    " + rep["id"]["id"])


def req2():
    fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
    print("fetch_limit 3: ")
    fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3}, headers=headers)
    print("     " , fetch_r.json())
    print(fetch_r.status_code, 200)
    print(len(fetch_r.json()), 3)
    print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True) == True)
    for rep in fetch_r.json():
        print(rep["type"] + "    " + rep["id"]["id"])

    key1 = fetch_r.json()[-1]["id"]["id"]
    fetch_1_json = [report_inventarisation, report_release, report_replenish, report_shipment, report_wsreplenish]
    print("fetch_limit_3_key: " + key1)
    fetch_r = requests.get(consts.API_REPORT_PREFIX + "fetch", params = {"count": 3, "key": key1}, headers=headers)
    print("     " , fetch_r.json())
    print(fetch_r.status_code, 200)
    print(len(fetch_r.json()), 2)
    print(ft.reduce(lambda a,rep: a and (rep in fetch_1_json), fetch_r.json(), True) == True)
    fetch_r.json()
    for rep in fetch_r.json():
        print(rep["type"] + "    " + rep["id"]["id"])

