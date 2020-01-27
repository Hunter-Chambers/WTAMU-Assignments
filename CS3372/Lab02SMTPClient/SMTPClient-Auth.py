#!/usr/bin/python

import socket
import ssl
import base64
import getpass


def main():
    choice = ""
    while (choice != "Y" and choice != "N"):
        serverName = input("Please input the server you wish to connect to: ")
        choice = input("Will you need to authenticate " +
                       "on this server? (Y/N) ").upper()
        if (choice == "Y"):
            # use this port if authentication is required
            port = 465
        elif (choice == "N"):
            port = 25
        else:
            input("\nInvalid option. Press Enter to continue...")
            print()

    mailServer = (serverName, port)
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    print("\nConnecting to", mailServer[0], end='......')

    if (choice == "Y"):
        # establish an ssl socket if authentication is required
        clientSocket = ssl.wrap_socket(clientSocket)

    try:
        clientSocket.connect(mailServer)
        print("Connected!\n")

        # receive response from server
        response = clientSocket.recv(4096).decode()
        # print("Server response after connection request:", response)
    except socket.error:
        print("Failed to connect")
        print()
        # define response if failed to connect
        response = "550"

    # if status code 220 OK was sent
    if (response[:3] == "220"):
        # EHLO begins smtp server/client interaction
        clientSocket.send(b"EHLO SMTPClient\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after EHLO command:", response)

        if (choice == "Y"):
            clientSocket.send(b"AUTH LOGIN\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after AUTH LOGIN command:", response)

            usr = input("Please input your username: ")
            print(usr)
            usr = usr.encode()
            usr = base64.b64encode(usr)
            clientSocket.send(usr)
            clientSocket.send(b"\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after user sent:", response)

            pwd = getpass.getpass("Please input your password: ")
            pwd = pwd.encode()
            pwd = base64.b64encode(pwd)
            clientSocket.send(pwd)
            clientSocket.send(b"\r\n")
            response = clientSocket.recv(4096).decode()
            print("Message after pwd sent:", response)

        # wait until user enters valid email
        valid = False
        while (not valid):
            sender = input("Please input the sender's email address: ")
            print()

            # send MAIL FROM command
            fromCode = ("MAIL FROM:<" + sender + ">\r\n")
            clientSocket.send(fromCode.encode())

            response = clientSocket.recv(4096).decode()
            if (response[:3] != "250"):
                print("Invalid email address.")
            else:
                valid = True
            # print("Server response after MAIL FROM command:", response)

        # wait until user enters valid email
        valid = False
        while (not valid):
            recipient = input("Please input the recipient's email address: ")
            print()

            # send RCPT TO command
            toCode = ("RCPT TO:<" + recipient + ">\r\n")
            clientSocket.send(toCode.encode())

            response = clientSocket.recv(4096).decode()
            if (response[:3] != "250"):
                print("Invalid email address.")
            else:
                valid = True
            # print("Server response after RCPT TO command:", response)

        # send DATA command to start email information
        clientSocket.send(b"DATA\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after DATA command:", response)

        # send From info
        fromInfo = ("From: <" + sender + ">\r\n")
        clientSocket.send(fromInfo.encode())

        # send To info
        toInfo = ("To: <" + recipient + ">\r\n")
        clientSocket.send(toInfo.encode())

        # send Subject info
        subject = input("Please enter a subject for the email: ")
        subjectInfo = ("Subject: " + subject + "\r\n")
        clientSocket.send(subjectInfo.encode())

        print()

        # send the actual message of the email
        print("Please input the body of the email.")
        print("Enter '.' (without the quotes) on a line by",
              "itself to finish the message.")

        # continue sending user input until user
        # sends a '.' on a line by itself
        msg = ""
        while (msg != ".\r\n"):
            msg = input()
            msg = (msg + "\r\n")
            clientSocket.send(msg.encode())

        response = clientSocket.recv(4096).decode()

        print()

        # display if the server accepted the email or not
        print("Server response after email has been sent:", response)

        # close connection
        clientSocket.send(b"QUIT\r\n")
        response = clientSocket.recv(4096).decode()
        # print("Server response after quit request:", response)
    else:
        print("Status code 220 was not received.")

    # close socket
    clientSocket.close()

    print("Program will now close.")
    input("Press Enter to continue...")


if (__name__ == "__main__"):
    main()
