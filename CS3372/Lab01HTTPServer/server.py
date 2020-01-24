#!/usr/bin/env python3
import socket


def main():
    serverPort = 8080
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', serverPort))
    serverSocket.listen(1)
    print("The server is ready to receive")
    connectionSocket, addr = serverSocket.accept()
    # sentence = connectionSocket.recv(1024).decode()

    connectionSocket.send(b"HTTP/1.1 200 OK\n")
    connectionSocket.send(b"Content-Type: text/html\n\n")
    f = open("index.html")
    connectionSocket.send(f.read().encode())
    f.close()

    serverSocket.close()


if (__name__ == "__main__"):
    main()
