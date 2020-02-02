#!/usr/bin/env python3
import socket  # used to create the web server
import time    # used to pause briefly before server shutdown
import sys     # used to accept command line input


def send200(connectionSocket):
    # send status code 200
    connectionSocket.send(("HTTP/1.1 200 OK\r\n").encode())

    # return the type of content that is
    # about to come through
    connectionSocket.send(("Content-Type: text/html\r\n\r\n").encode())


def send404(connectionSocket):
    # send status code 404
    connectionSocket.send(("HTTP/1.1 404 NOT FOUND\r\n").encode())

    # send the type of content that is
    # about to come through
    connectionSocket.send(("Content-Type: text/html\r\n\r\n").encode())

    # send basic 404 message
    connectionSocket.send(("<!DOCTYPE html><html><body><h1>" +
                           "Error 404: File Not Found" +
                           "</h1></body></html>").encode())


def main():
    # default to port 8080
    serverPort = 8080

    if (len(sys.argv) > 1):
        # check if user specified a port
        try:
            # change to the port that
            # the user has specified
            serverPort = int(sys.argv[1])
        except ValueError:
            print("Invalid port number. Port will default to 8080.")
            input("Press Enter to continue...")

    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    serverSocket.bind(('', serverPort))
    serverSocket.listen(1)
    print("The server is ready to receive")

    # establish connection
    connectionSocket, addr = serverSocket.accept()

    # receive request from client and split() it for easy parsing
    request = connectionSocket.recv(4096).decode().split('\n')

    if ("GET" == request[0][:3]):
        # if the client has made a GET request,
        # split() line one of the request to parse out the requested file
        lineOne = request[0].split(' ')

        # add '.' to the front to correct the directory path
        fileRequest = ('.' + lineOne[1])

        if (fileRequest == "./"):
            # if the client requested / (root),
            # send status code 200
            send200(connectionSocket)

            # send root page info
            connectionSocket.send(("<!DOCTYPE html><html><body><h1>" +
                                   "Welcome to my page!" +
                                   "</h1></body></html>").encode())
        else:
            try:
                # try opening the requested file
                f = open(fileRequest)

                # if file opened successfully,
                # return status code 200
                send200(connectionSocket)

                # send contents of the requested file
                connectionSocket.send(f.read().encode())

                # close file
                f.close()
            except Exception:
                # send 404 if error occured
                send404(connectionSocket)
    else:
        # GET request was not made, send 404
        send404(connectionSocket)

    # waiting 1sec ensures all content can be
    # sent in time before this server closes
    time.sleep(1)

    # shutdown and close socket
    serverSocket.close()


if (__name__ == "__main__"):
    main()
