package com.alcance.predictivemta.infrastructure;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.alcance.predictivemta.dvo.Campaing;

public class CampaingProgressSocket extends MessageInbound {
	private WsOutbound outbound;
	private ScheduledExecutorService executor = Executors
			.newSingleThreadScheduledExecutor();
	private boolean closed = false;
	private Campaing _campaing;

	public CampaingProgressSocket(Campaing campaing) {
		_campaing = campaing;
	}

	@Override
	public void onOpen(final WsOutbound outbound) {
		this.outbound = outbound;
		// System.out.println("socket opened!");
		  executor.scheduleAtFixedRate( new Monitor(this), 2, 2,
		 TimeUnit.SECONDS);
	}

	@Override
	public void onTextMessage(CharBuffer buffer) throws IOException {
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
	}

	public WsOutbound getOutBound() {
		return this.outbound;
	}

	@Override
	protected void onClose(int status) {
		super.onClose(status);
		closed = true;
	}

	public boolean isClosed() {
		return closed;
	}
	
	public Campaing getCampaing() {
		return _campaing;
	}

	private class Monitor implements Runnable {
		private CampaingProgressSocket _webSocket;

		public Monitor(CampaingProgressSocket socket) {
			_webSocket = socket;
		}

		@Override
		public void run() {
			try {
				String message = _webSocket.getCampaing().getMailSentProgress()+"%";
				if (!_webSocket.isClosed()) {
					_webSocket.getOutBound().writeTextMessage(
							CharBuffer.wrap(message.toCharArray()));
					if(message.equalsIgnoreCase("100%")) {
						_webSocket.getWsOutbound().close(0, ByteBuffer.wrap("Finished".getBytes()));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}
}
