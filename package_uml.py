

FINAL = "FINAL"
depth_treshold = 5
ignore = ["com.hexi.Cerberus.infrastructure","com.hexi.Cerberus.config", "com.hexi.Cerberus.adapter.persistence.config"]

def gen_pack_hierarchy(base_dict, path, term):
    if len(path) > 0 and path[0] not in base_dict.keys():
        base_dict[path[0]] = {}
    if (len(path) > 1):
        gen_pack_hierarchy(base_dict[path[0]], path[1:], term)
    else:
        base_dict[path[0]].update({term : FINAL})

def gen_node_tree(hier, term, depth):
    if hier == FINAL:
        return ""
    depth_tab = " " * depth
    strb = depth_tab + f'package "{term}" ' + "{\n"
    for key in hier.keys():
        pl = gen_node_tree(hier[key], term + '.' + key, depth + 1)
        strb = strb + pl
    strb = strb + depth_tab + "}\n"
    return strb

packages = list(open("packages_full.txt"))
imports = list(open("imports_full.txt"))

hier = {}
for pack in packages:
    term, path = pack.replace('\n', '').split(' -> ')
    for i in ignore:
        if i in path:
            break
    else:
        path = path.split('.')[:depth_treshold]
        gen_pack_hierarchy(hier, path, term)

umlstart = "@startuml\n skinparam linetype ortho\n"
umlend = "@enduml\n"

umlnodebody = gen_node_tree(hier['com']['hexi']['Cerberus'], "com.hexi.Cerberus", 1) + "\n"

umllinks = list()

for imp in imports:
    term1, path1 = imp.replace('\n', '').split(' -> ')
    path1 = '.'.join(path1.split('.')[:depth_treshold])
    for i in ignore:
        if i in path1:
            break
    else:
        for pack in packages:
            term2, path2 = pack.replace('\n', '').split(' -> ')
            path2 = '.'.join(path2.split('.')[:depth_treshold])
            for i in ignore:
                if i in path2:
                    break
            else:
                if (term1 == term2) and (path1 != path2):
                    umllinks.append("\"" + path2 + "\"" + " --> " + "\"" + path1 + "\"")

umllinks = "\n".join(sorted(set(umllinks))) + "\n"


print(umlstart + umlnodebody + umllinks + umlend)
