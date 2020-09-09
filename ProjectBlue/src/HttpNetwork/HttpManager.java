package HttpNetwork;

import Data.HangleData;
import Developer.DeveloperManager;
import Enums.PrintType;

public class HttpManager {
	
	/*
	 * ====================================================================================
	 * Kakao API를 사용하기 위해 HttpConnector 객체를 생성하고,
	 * 이 객체를 관리하는 Manager 클래스이다.
	 * ====================================================================================
	 */
	// variable
	private HttpConnector connector;

	// Constructor
	public HttpManager(HttpConnector connector) {
		this.connector = connector;
	}

	// method
	public String startManager() {
		String response = null;

		connector.openConnection();
		// 1. Whether connect server 
		if (connector.isConnectable()) {
			connector.openStream();
			// 2. Whether get input stream
			if (connector.isStreamable()) {
				response = connector.getStreamResponse();
				// 3. Whether success response message getting
				if (connector.isSuccessable()) {
					connector.close();
					DeveloperManager.printDeveloperMessage("HttpManager / F / httpURLConnection object disconnect() success");
				} else {
					DeveloperManager.printDeveloperMessage("HttpManager / " +HangleData.HttpManager_STEP_3);
				}
			} else {
				DeveloperManager.printDeveloperMessage("HttpManager / " +HangleData.HttpManager_STEP_2);
			}
		} else {
			DeveloperManager.printDeveloperMessage("HttpManager / " +HangleData.HttpManager_STEP_1);
		}
		return response;
	}
}
