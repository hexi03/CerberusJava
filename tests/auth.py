import consts
import requests
def get_auth_token():
    auth_r = requests.post(consts.API_AUTH_PREFIX + "login", json = consts.AUTH_CREDS)
    print(auth_r.status_code)
    print(auth_r.content)
    print(auth_r.json())
    return auth_r.json()["token"]