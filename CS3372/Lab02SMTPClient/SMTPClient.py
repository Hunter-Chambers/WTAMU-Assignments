#!/usr/bin/python

import socket
import ssl
import base64
import getpass


def main():
    choice = ""
    while (choice != "Y" and choice != "N"):
        serverName = input("Please input the server you wish to connect to: ")
        choice = input("Will you need to authenticate on this server? (Y/N) ").upper()
        if (choice == "Y"):
            port = 465
        elif (choice == "N"):
            port = 25
        else:
            trash = input("\nInvalid option. Press Enter to continue...")
            print()

    mailServer = (serverName, port)
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    print("\nConnecting to", mailServer[0], end='......')

    if (choice == "Y"):
        clientSocket = ssl.wrap_socket(clientSocket)

    try:
        clientSocket.connect(mailServer)
        print("Connected!\n")
        response = clientSocket.recv(4096).decode()
        print("Message after connection request:", response)
    except socket.error:
        trash = input("Failed to connect\nPress Enter to continue...")
        print()
        response = "550"

    if (response[:3] == "220"):
        sender = input("Please input the sender's email address: ")
        ehlo = ("EHLO " + sender + "\r\n")
        ehlo = ("EHLO Hunter.com\r\n")
        clientSocket.send(ehlo.encode())
        response = clientSocket.recv(4096).decode()
        print("Message after EHLO command:", response)

        if (choice == "Y"):
            clientSocket.send(b"AUTH LOGIN\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after AUTH LOGIN command:", response)

            usr = "hrchambers.333@gmail.com"
            #usr = input("Please input your username: ")
            print(usr)
            usr = usr.encode()
            usr = base64.b64encode(usr)
            clientSocket.send(usr + b"\r\n")
            #clientSocket.send(b"\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after user sent:", response)

            pwd = getpass.getpass("Please input your password: ")
            pwd = pwd.encode()
            pwd = base64.b64encode(pwd)
            clientSocket.send(pwd)
            clientSocket.send(b"\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after pwd sent:", response)
        pass

        clientSocket.send(b"MAIL FROM:<hrchambers.333@gmail.com>\r\n")
        response = clientSocket.recv(4096).decode()
        print("Message after MAIL FROM command:", response)

        clientSocket.send(b"RCPT TO:<hrchambers.333@gmail.com>\r\n")
        response = clientSocket.recv(4096).decode()
        print("Message after RCPT TO command:", response)

        clientSocket.send(b"DATA\r\n")
        response = clientSocket.recv(4096).decode()
        print("Message after DATA command:", response)

        clientSocket.send(b"From: <hrchambers.333@gmail.com\r\n")
        clientSocket.send(b"To: <hrchambers.333@gmail.com\r\n")
        clientSocket.send(b"Subject: This is just a test\r\n")
        msg = input("Please input the body of the email:\n")
        clientSocket.send(msg.encode())
        clientSocket.send(b".\r\n")
        response = clientSocket.recv(4096).decode()
        print("Message after msg has been sent:", response)

        clientSocket.send(b"QUIT\r\n")
        response = clientSocket.recv(4096).decode()
        print("Message after quit request:", response)
    else:
        print("Status code 220 was not received.")

    input("Program will now close.\nPress Enter to continue...")

    clientSocket.close()


if (__name__ == "__main__"):
    main()
