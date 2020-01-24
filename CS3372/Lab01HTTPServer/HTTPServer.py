#!/usr/bin/env python3
#from socket import *
import socket


def main():
	serverPort = 8080
	print("HERE")
	serverSocket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
	serverSocket.bind(('', serverPort))
	print("The server is ready to receive")
	while (True):
		message, clientAddr = serverSocket.recvfrom(2048)
		modifiedMessage = message.decode().upper()
		serverSocket.sendto(modifiedMessage.encode(), clientAddr)


if (__name__ == "__main__"):
	main()
