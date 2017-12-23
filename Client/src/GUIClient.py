from tkinter import *

import requests


class App:
    def __init__(self, master):
        self.result = ""

        self.port = StringVar()
        self.host = StringVar()
        Entry(master, textvariable=self.host).grid(row=0, column=1)

        self.host.set("localhost")
        Entry(master, textvariable=self.port).grid(row=0, column=2)

        self.port.set(8080)
        Button(master, text="GET", command=self.__connectCallback).grid(row=0, column=3)

        self.listbox = Listbox(master, width=70)
        self.listbox.grid(row=1, column=0, columnspan=4)

    def __connectCallback(self):
        url = self.__buildURL()
        resp = requests.get(url)
        self.result = resp.json()
        self.listbox.delete(0, END)
        for key, value in self.result.items():
            insert = "Sensor {} : {}".format(key, value)
            self.listbox.insert(END, insert)

    def __buildURL(self):
        res = "http://" + self.host.get()
        res += ":" + self.port.get()
        res += "/parking"
        return res


if __name__ == "__main__":
    root = Tk()
    App(root)
    mainloop()
