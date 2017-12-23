import requests


class SimplePythonClient:
    def __init__(self, host, port):
        self.host = host
        self.port = port

    def getData(self):
        url = self.__buildURL()
        resp = requests.get(url)
        data = resp.json()
        print(data)

    def __buildURL(self):
        url = "http://" + self.host
        url += ":" + str(self.port)
        url += "/parking"
        return url


if __name__ == "__main__":
    pc = SimplePythonClient("localhost", 8080)
    pc.getData()
