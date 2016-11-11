package poker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.DeckException;
import netgame.common.Hub;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.CardDraw;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerBase.Table;
import pokerEnums.eAction;
import pokerEnums.eCardDestination;
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eGameState;

public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;
	private eGameState eGameState;

	public PokerHub(int port) throws IOException {
		super(port);
	}

	protected void playerConnected(int playerID) {

		if (playerID == 2) {
			shutdownServerSocket();
		}
	}

	protected void playerDisconnected(int playerID) {
		shutDownHub();
	}

	protected void messageReceived(int ClientID, Object message) {

		if (message instanceof Action) {
			if (message instanceof Action) {
				Action act = (Action) message;
				switch (act.getAction()) {
				case Sit:
					resetOutput();
					HubPokerTable.AddPlayerToTable(act.getPlayer());				
					sendToAll(HubPokerTable);				
					break;
				case Leave:
					resetOutput();
					HubPokerTable.RemovePlayerFromTable(act.getPlayer());
					sendToAll(HubPokerTable);				
					break;
				case GameState:
					sendToAll(HubPokerTable);
					break;
				case TableState:
					resetOutput();
					sendToAll(HubPokerTable);
					break;
				}
			}
			
		}

		System.out.println("Message Received by Hub");
		
		sendToAll("Sending Message Back to Client");
	}

}
