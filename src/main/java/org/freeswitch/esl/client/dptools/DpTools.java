package org.freeswitch.esl.client.dptools;

import org.freeswitch.esl.client.internal.IModEslApi;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DpTools extends Execute {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final IModEslApi api;
	private final String uuid;

	public DpTools(IModEslApi api) {
		this(api, null);
	}

	public DpTools(IModEslApi api, String uuid) {
		super(api, uuid);
		this.api = api;
		this.uuid = uuid;
	}

	@Override
	protected CommandResponse sendExeMesg(String app, String args)
			throws ExecuteException {

		log.debug("sendExeMesg {} {}", app, args);

		SendMsg msg = createMsgObject();
		msg.addCallCommand("execute");
		msg.addExecuteAppName(app);

		if (args != null)
			msg.addExecuteAppArg(args);
		CommandResponse resp = api.sendMessage(msg);

		if (!resp.isOk())
			throw new ExecuteException(resp.getReplyText());
		else
			return resp;
	}

	private SendMsg createMsgObject() {
		return uuid == null ? new SendMsg() : new SendMsg(uuid);
	}

}
