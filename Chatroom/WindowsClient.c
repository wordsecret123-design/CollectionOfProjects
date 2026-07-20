#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <Windows.h>
#include <winsock.h>
#define PORT 9909


int nClientSocket;
char buff[20000] = { 0, };

LRESULT CALLBACK WindowProcedure(HWND, UINT, WPARAM, LPARAM);

void AddControls(HWND);

void AddText(LPCWSTR message);

void encryption(char* message) {

	for (int i = 0;i < strlen(message);i++) {

		if (message[i] % 2 == 0) {
			message[i] = message[i] + 3;
		}
		else {
			message[i] = message[i] - 3;
		}


	}

}

void decryption(char* message) {

	for (int i = 0;i < strlen(message);i++) {

		if (message[i] % 2 == 0) {
			message[i] = message[i] + 3;
		}
		else {
			message[i] = message[i] - 3;
		}


	}

}


HWND hMessage,hViewMessage, hName;


wchar_t* sentMessage;
wchar_t* name;
char* buffer;
char* received;


DWORD WINAPI rcv(LPVOID lpParam) {

	while (1) {
		received = (char*)malloc(20000 * sizeof(char));
		memset(received, '\0', 20000);
		recv(nClientSocket, received, 20000, 0);
		decryption(received);
		SendMessageA(hViewMessage, EM_REPLACESEL, 0, (LPARAM)received);
		free(received);
	}
	return 0;
}

DWORD WINAPI whileGetMessage(LPVOID lpParam) {

	MSG msg = { 0 };

	while (GetMessage(&msg, NULL, 0, 0)) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);

	}
	return 0; 
}

int WINAPI WinMain(HINSTANCE hInst, HINSTANCE hPrevInst, LPSTR args, int ncmdshow)
{

	{
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


		nClientSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
		if (nClientSocket < 0) {
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



		nRet = connect(nClientSocket, (const struct sockaddr*)&srv, sizeof(srv));
		if (nRet < 0) {
			printf("Connect failed. \n");
			WSACleanup();
			exit(1);
		}
		else {
			printf("Connected succesfully. \n");

		}
	}


	
	WNDCLASSW wc = { 0 };

	wc.hbrBackground = (HBRUSH)COLOR_WINDOW;
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);
	wc.hInstance = hInst;
	wc.lpszClassName = L"myWindowClass";
	wc.lpfnWndProc = WindowProcedure;

	if(!RegisterClassW(&wc))
		return -1;

	CreateWindowW(L"myWindowClass", L"Chat App", WS_OVERLAPPEDWINDOW | WS_VISIBLE, 100, 100, 600, 600,
		NULL, NULL, NULL, NULL);

	HANDLE hThread;
	DWORD ThreadID; 

	hThread = CreateThread(NULL, 0, rcv, NULL, 0, &ThreadID);
	
	MSG msg = { 0 };

	while (GetMessage(&msg, NULL, 0, 0)) {
		
		

		TranslateMessage(&msg);
		DispatchMessage(&msg);

		

	}

	return 0;
}

LRESULT CALLBACK WindowProcedure(HWND hWnd, UINT msg, WPARAM wp, LPARAM lp) {

	switch (msg) {
	case WM_COMMAND:
		switch (wp) {
		case 1:

			sentMessage = (wchar_t*)malloc(20000 * sizeof(wchar_t));
			 
			name = (wchar_t*)malloc(20000 * sizeof(wchar_t));
			buffer = (char*)malloc(20000 * sizeof(char));
			size_t i;
			
			//sentMessage = (char*)malloc(20000 * sizeof(char));
			//name = (char*)malloc(20000 * sizeof(char));
			
			GetWindowTextW(hName, name, GetWindowTextLength(hName) + 1);

			
			GetWindowTextW(hMessage, sentMessage, GetWindowTextLength(hMessage) + 1);
			
			/*
			strcat_s(name, 20000, L": ");
			strcat_s(sentMessage, 20000, L"\r\n");
			strcat_s(name, 20000, sentMessage);
			*/

			
			wcscat_s(name, 20000, L": ");
			wcscat_s(sentMessage, 20000, L"\r\n");
			wcscat_s(name, 20000, sentMessage);
			
			wcstombs_s(&i, buffer, (size_t)20000, name, (size_t)20000 - 1);

			
			
			encryption(buffer);
			send(nClientSocket, buffer, 20000, 0); 
			SetWindowTextW(hMessage, L"");
			AddText(name);
			memset(name, 0, 20000);
			free(sentMessage);
			free(name);
			free(buffer);
			break;
		}
		break;
	case WM_CREATE:
		AddControls(hWnd);
		break;
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
	default:
		return DefWindowProcW(hWnd, msg, wp, lp);

	}


}

void AddControls(HWND hWnd) {

	CreateWindowW(L"Static", L"Name:", WS_VISIBLE | WS_CHILD, 20, 50, 50, 20,
		hWnd, NULL, NULL, NULL);
	hName = CreateWindowW(L"Edit", L"", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_MULTILINE | WS_VSCROLL, 72, 50, 400, 20,
		hWnd, NULL, NULL, NULL);

	hMessage = CreateWindowW(L"Edit", L"", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_MULTILINE | WS_VSCROLL, 20, 470, 500, 50,
		hWnd, NULL, NULL, NULL);

	CreateWindowW(L"Button", L"Send", WS_VISIBLE | WS_CHILD, 521, 470, 50, 50, hWnd, (HMENU)1, NULL, NULL);

	hViewMessage = CreateWindowW(L"Edit", L"", WS_VISIBLE | WS_CHILD | WS_BORDER | ES_MULTILINE | WS_VSCROLL, 20, 100, 500, 350,
		hWnd, NULL, NULL, NULL);

}

/*void AddText(char* message) {

	char LogText[200] = { 0, };

	strcat_s(LogText, sizeof("\n"), "\n");
	strcat_s(LogText, sizeof(message), message);

	SetWindowText(hViewMessage, message);


	
}*/

void AddText(LPCWSTR newText)
{
	//DWORD l, r;
	//LPCTSTR newLine = "\n";
	int len = GetWindowTextLength(hViewMessage);
	//SendMessage(hViewMessage, EM_GETSEL, (WPARAM)&l, (LPARAM)&r);
	//SendMessage(hViewMessage, EM_SETSEL, len, len);
	SendMessageW(hViewMessage, EM_REPLACESEL, 0, (LPARAM)newText); 
	//SendMessage(hViewMessage, EM_REPLACESEL, 0, (LPARAM)newLine);
	//SendMessage(hViewMessage, EM_SETSEL, 50, 200);
}