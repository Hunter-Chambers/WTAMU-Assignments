#!/usr/bin/env python3
import socket
import time

WINDOWS_NL = "\r\n"
UNIX_NL = "\n"

def main():
    serverPort = 8080
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', serverPort))
    serverSocket.listen(1)
    print("The server is ready to receive")

    connectionSocket, addr = serverSocket.accept()
    request = connectionSocket.recv(1024).decode()
    request = request.split('\n')

    if ("GET" in request[0]):
        if ('\r' in request[0]):
            ending = WINDOWS_NL
        else:
            ending = UNIX_NL

        if (' / ' in request[0] or ' /index.html ' in request[0]):
            status = "HTTP/1.1 200 OK" + ending
            header = "Content-Type: text/html" + ending + ending
            connectionSocket.send(status.encode())
            connectionSocket.send(header.encode())
            f = open("index.html")
            connectionSocket.send(f.read().encode())
            f.close()
        else:
            connectionSocket.send(("HTTP/1.1 404 NOT FOUND" + ending).encode())
            connectionSocket.send(("Content-Type: text/html" + ending + ending).encode())
            connectionSocket.send(b"<!DOCTYPE html><html><body><p>Error 404: File Not Found</p></body></html>")
        time.sleep(1)

    serverSocket.close()


if (__name__ == "__main__"):
    main()
