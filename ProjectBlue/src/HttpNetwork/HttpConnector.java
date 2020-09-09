package HttpNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Data.HangleData;
import Developer.DeveloperManager;
import Enums.PrintType;
import Enums.RequestType;

public class HttpConnector {
	
	/*
	 * ====================================================================================
	 * Kakao API를 사용하기 위해 HttpURLConnector으로 통신연결을 하는 Connecotr 클래스이다.
	 * ====================================================================================
	 */
	// variable
	private String domain;
	private RequestType requestType;
	private String dataType;
	private HashMap<String, String> headerData;

	private URL url;
	private HttpURLConnection connector;
	private BufferedReader reader;

	private boolean connectable;
	private boolean streamable;
	private boolean successable;

	// Constructor
	public HttpConnector(String domain, RequestType requestType, String dataType) {
		this.domain = domain;
		this.requestType = requestType;
		this.dataType = dataType;
		this.connectable = false;
		this.streamable = false;
		this.successable = false;
	}

	public HttpConnector(String domain, RequestType requestType, String dataType, HashMap<String, String> headerData) {
		this.domain = domain;
		this.requestType = requestType;
		this.dataType = dataType;
		this.headerData = headerData;
		this.connectable = false;
		this.streamable = false;
		this.successable = false;
	}

	// method : getter, setter
	public boolean isConnectable() {
		return connectable;
	}

	public boolean isStreamable() {
		return streamable;
	}

	public boolean isSuccessable() {
		return successable;
	}

	// method
	// step1. Http Newtork Connection
	public void openConnection() {
		try {
			DeveloperManager.printDeveloperMessage("HttpConnector / F / domain : " + domain);
			url = new URL(domain);
			connector = (HttpURLConnection) url.openConnection();

			if (requestType == RequestType.POST) {
				connector.setRequestMethod("POST");
			} else if (requestType == RequestType.GET) {
				connector.setRequestMethod("GET");
			}

			if (headerData != null) {
				Iterator iterator = headerData.entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					connector.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
					DeveloperManager.printDeveloperMessage("HttpConnector / data name : " + entry.getKey() + ", value : " + entry.getValue());
				}
			} else {
				DeveloperManager.printDeveloperMessage("HttpConnector / " + HangleData.HttpConnector_HEADER_NOTHING_MSSEAGE);
			}
			connector.setDoInput(true);
			connectable = true;
		} catch (MalformedURLException mue) {
			connectable = false;
			mue.printStackTrace();
		} catch (IOException ioe) {
			connectable = false;
			ioe.printStackTrace();
		}
	}

	// step2. get input stream
	public void openStream() {
		try {
			if (reader == null) {
				reader = new BufferedReader(new InputStreamReader(connector.getInputStream(), "UTF-8"));
				streamable = true;
			} else {
			}
		} catch (IOException ioe) {
			DeveloperManager.printDeveloperMessage("HttpConnector / error / 뭐냐 왜 못 가져오냐?");
			ioe.printStackTrace();
		}
	}

	// step3. get response data
	public String getStreamResponse() {
		StringBuilder response = new StringBuilder();
		if (reader != null) {
			if (dataType.equals("Json")) {
				oneLine(response);
			} else if (dataType.equals("JsonArray")) {
				oneLine(response);
			} else if (dataType.equals("OneLine")) {
				oneLine(response);
			} else if (dataType.equals("Html")) {
				unlimitedLine(response);
			} else if (dataType.equals("UnlimitedLine")) {
				unlimitedLine(response);
			} else {
				DeveloperManager.printDeveloperMessage("HttpConnector / " + HangleData.HttpConnector_NOTING_DATATYPE_MESSAGE);
			}
		}
		return response.toString();
	}

	// step4. httpURLConnection object disconnect
	public void close() {
		
		// input stream close
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		// HttpURLConnection object disconnection
		connector.disconnect();
	}

	// 3-1. response data type : one line
	private void oneLine(StringBuilder response) {
		try {
			response.append( reader.readLine());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				reader.close();
				successable = true;
			} catch (IOException closeIOE) {
				closeIOE.printStackTrace();
			}
		}
	}

	// 3-2. response data type : more and more line
	private void unlimitedLine(StringBuilder response) {
		String temp = null;
		try {
			while ((temp = reader.readLine()) != null) {
				response.append(temp);
				response.append("\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				reader.close();
				successable = true;
			} catch (IOException closeIOE) {
				closeIOE.printStackTrace();
			}
		}
	}

	// method : http connection one step
	public String start() {
		StringBuilder responseMessage = new StringBuilder();

		try {
			// 1. HttpURLConnection object creation
			url = new URL(domain);
			connector = (HttpURLConnection) url.openConnection();

			// 2. connection setting - request method 'GET' or 'POST'
			if (requestType == RequestType.POST) {
				connector.setRequestMethod("POST");
			} else if (requestType == RequestType.GET) {
				connector.setRequestMethod("GET");
			}

			// 3. connection setting - request add header 
			if (headerData != null) {
				Iterator iterator = headerData.entrySet().iterator();

				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					connector.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
					DeveloperManager.printDeveloperMessage("data name : " + entry.getKey());
					DeveloperManager.printDeveloperMessage("value : " + entry.getValue());
				}
			} else {
				DeveloperManager.printDeveloperMessage(HangleData.HttpConnector_HEADER_NOTHING_MSSEAGE);
			}

			// 4. connection setting - request get input stream
			connector.setDoInput(true);

			// 5. GET or POST choice
			if (requestType == RequestType.POST) {
				connector.setRequestMethod("POST");
			} else if (requestType == RequestType.GET) {
				connector.setRequestMethod("GET");
			}

			// 6. open stream
			reader = new BufferedReader(new InputStreamReader(connector.getInputStream()));

			// 7. response 
			String temp;
			if (reader != null) {
				while ((temp = reader.readLine()) != null) {
					// return 내용 채우기
					responseMessage.append(temp);
					responseMessage.append("\n");
				}
				reader.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connector != null) {
				connector.disconnect();
			}
		}
		return responseMessage.toString();
	}
}
