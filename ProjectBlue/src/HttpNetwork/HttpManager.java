package HttpNetwork;

import Data.HangleData;
import Enums.PrintType;

public class HttpManager {

	private final PrintType PRINT_TYPE = PrintType.ON;
	
	private HttpConnector connector;

	public HttpManager(HttpConnector connector) {
		this.connector = connector;
	}

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
					printDeveloperMessage("HttpManager : httpURLConnection object disconnect() success");
				} else {
					printDeveloperMessage("HttpManager : " +HangleData.HttpManager_STEP_3);					
				}
			} else {
				printDeveloperMessage("HttpManager : " +HangleData.HttpManager_STEP_2);					
			}
		} else {
			printDeveloperMessage("HttpManager : " +HangleData.HttpManager_STEP_1);					
		}
		return response;
	}

	// print developer message
	private void printDeveloperMessage(String message) {
		if (PRINT_TYPE == PRINT_TYPE.ON) {
			System.out.println(message);
		} else {

		}
	}
}
