#!/usr/bin/env python3
import socket
import time
import sys

# use this as line-ending if '\r'
# is found in the request
WINDOWS_NL = "\r\n"

# otherwise use this line-ending
UNIX_NL = "\n"


def main():
    # default to port 8080
    serverPort = 8080
    if (len(sys.argv) > 1):
        try:
            # change to the port that
            # the user has specified
            serverPort = int(sys.argv[1])
        except ValueError:
            print("Invalid port number. Port will default to 8080.")
            input("Press Enter to continue...")

    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', serverPort))
    serverSocket.listen(1)
    print("The server is ready to receive")

    # establish connection
    connectionSocket, addr = serverSocket.accept()

    # receive request from client
    request = connectionSocket.recv(4096).decode()

    # if the client has made a GET request
    if ("GET" == request[:3]):
        # update our line-ending based
        # on which one exists in the request
        if ('\r' in request):
            ending = WINDOWS_NL
        else:
            ending = UNIX_NL

        # if the client has requested / or /index.html
        if (' / ' == request[3:6] or ' /index.html ' == request[3:16]):
            # return status code 200
            status = "HTTP/1.1 200 OK" + ending
            connectionSocket.send(status.encode())

            # return the type of content that is
            # about to come through
            header = "Content-Type: text/html" + ending + ending
            connectionSocket.send(header.encode())

            # send contents of file
            f = open("index.html")
            connectionSocket.send(f.read().encode())
            f.close()
        else:
            # if the client has not requested the right file,
            # send status code 404
            connectionSocket.send(("HTTP/1.1 404 NOT FOUND" + ending).encode())

            # send the type of content that is
            # about to come through
            connectionSocket.send(("Content-Type: text/html" +
                                   ending + ending).encode())

            # send basic 404 message
            connectionSocket.send(("<!DOCTYPE html><html><body><p>" +
                                   "Error 404: File Not Found" +
                                   "</p></body></html>").encode())

        # waiting 1sec ensures all content can be
        # sent in time before this server closes
        time.sleep(1)

    # shutdown and close socket
    serverSocket.shutdown(socket.SHUT_RDWR)
    serverSocket.close()


if (__name__ == "__main__"):
    main()
