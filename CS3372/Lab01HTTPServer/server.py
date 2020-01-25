#!/usr/bin/env python3
import socket
import time


def main():
    serverPort = 8080
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', serverPort))
    serverSocket.listen(1)
    print("The server is ready to receive")
    connectionSocket, addr = serverSocket.accept()
    # sentence = connectionSocket.recv(1024).decode()

    connectionSocket.send(b"HTTP/1.1 200 OK\r\n")
    connectionSocket.send(b"Content-Type: text/html\r\n\r\n")
    # f = open("index.html")
    f = open("Hello-php.php")
    connectionSocket.send(f.read().encode())
    time.sleep(1)
    f.close()

    serverSocket.close()


if (__name__ == "__main__"):
    main()
