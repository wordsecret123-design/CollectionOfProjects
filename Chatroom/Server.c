#include <stdio.h>
#include <winsock.h>

#define PORT 9909

int nMaxFd;
int nSocket;
int nClientSocket;
int nArrClient[6];
fd_set fr, fw, fe;

void ProcessTheNewRequest() {
	if (FD_ISSET(nSocket, &fr)) {
		int nLen = sizeof(struct sockaddr);
		nClientSocket = accept(nSocket, NULL, &nLen);
		if (nClientSocket > 0) {

			int i;
			for (i = 0;i < 6;i++) {
				if (nArrClient[i] == 0) {
					nArrClient[i] = nClientSocket;
					//send(nClientSocket, "Got the connection successfully.", 32, 0);
					break;
				}
			}
			if (i == 5) {
				printf("No space for a new connection.");
			}

		}
	}
	else {

		for (int i = 0;i < 6;i++) {
			if (FD_ISSET(nArrClient[i], &fr)) {
				char buffer[20000] = { 0, };
				recv(nArrClient[i], buffer, 20000, 0);
				

				for (int j = 0;j < 6;j++) {
					
					if(i!=j)
						send(nArrClient[j], buffer, strlen(buffer), 0);
				}

				memset(buffer, 0, 20000);
			}

		}
	}
}

int main() {

	WSADATA ws;
	int nMaxFd;

	int nRet = 0;

	struct sockaddr_in srv;


	if (WSAStartup(MAKEWORD(2, 2), &ws) < 0) {
		printf("WSA failed to initialize \n");
		WSACleanup();
		exit(1);
	}
	else {
		printf("WSA initialized \n");
	}


	nSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (nSocket < 0) {
		printf("The socket not opened \n");
		WSACleanup();
		exit(1);
	}
	else {
		printf("The socket opened successfully \n");
	}

	srv.sin_family = AF_INET;
	srv.sin_port = htons(PORT);
	srv.sin_addr.s_addr = inet_addr("127.0.0.1");
	// srv.sin_addr.s_addr = inet_addr("192.168.15.191");
	memset(&(srv.sin_zero), 0, 8);

	int nOptVal = 0;
	int nOptLen = sizeof(nOptVal);
	nRet = setsockopt(nSocket, SOL_SOCKET, SO_REUSEADDR, (const char*)&nOptVal, nOptLen);
	if (!nRet) {
		printf("The setsockopt call successful \n");

	}
	else {
		printf("The setsockopt call failed \n");
		WSACleanup();
		exit(1);
	}


	nRet = bind(nSocket, (const struct sockaddr*)&srv, sizeof(struct sockaddr_in));
	if (nRet < 0) {
		printf("Fail to bind to local port \n");
		WSACleanup();
		exit(1);
	}
	else {
		printf("Successfully bind to local port \n");
	}

	nRet = listen(nSocket, 6);
	if (nRet < 0) {
		printf("Fail to start listen to local port \n");
		WSACleanup();
		exit(1);
	}
	else {
		printf("Started listening to local port \n");
	}


	nMaxFd = nSocket;
	struct timeval tv;
	tv.tv_sec = 1;
	tv.tv_usec = 0;
	int i;
	while (1) {
		FD_ZERO(&fr);
		FD_ZERO(&fw);
		FD_ZERO(&fe);

		FD_SET(nSocket, &fr);
		FD_SET(nSocket, &fe);

		for (int i = 0;i < 6;i++) {

			if (nArrClient[i] != 0) {
				FD_SET(nArrClient[i], &fr);
				FD_SET(nArrClient[i], &fe);
			}
		}


		nRet = select(nMaxFd + 1, &fr, &fw, &fe, &tv);
		if (nRet > 0) {
			//printf("Data on port....Processing now...\n");

			ProcessTheNewRequest();
			//std::thread t1(ProcessTheNewRequest);
			
		}
		else if (nRet == 0)
		{
			//printf("Nothing on port %d\n", PORT);
		}
		else {
			printf("Failed");
			WSACleanup();
			exit(1);


		}

		

	}




	return 0;
}
