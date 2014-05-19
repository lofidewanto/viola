package com.colinalworth.gwt.viola.web.client;

import com.colinalworth.gwt.viola.web.client.ioc.Session.SessionProvider;
import com.colinalworth.gwt.viola.web.client.ioc.UserId.UserIdProvider;
import com.colinalworth.gwt.viola.web.client.ioc.ViolaGinjector;
import com.colinalworth.gwt.viola.web.client.mvp.ClientPlaceManager;
import com.colinalworth.gwt.viola.web.client.mvp.PushStateHistoryManager;
import com.colinalworth.gwt.viola.web.shared.mvp.ProfileEditorPresenter.ProfileEditorPlace;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ViolaApp implements EntryPoint {
	private SimpleContainer container = new SimpleContainer();
	private TextButton loginbtn;
	private TextButton userbtn;

	@Inject
	ClientPlaceManager placeManager;

	@Inject
	PushStateHistoryManager navigation;

	@Inject
	SessionProvider sessionManager;

	@Inject
	UserIdProvider userIdManager;

	private String userId;//hacky way to track, but this class hopefully won't grow much futher


	@Override
	public void onModuleLoad() {
		Viewport vp = new Viewport();

		vp.setWidget(mainApp());

		ViolaGinjector ginjector = GWT.create(ViolaGinjector.class);

		//TODO let gin create this so it can be injected elsewhere
		ginjector.inject(this);
		placeManager.setContainer(new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget w) {
				container.setWidget(w);
				container.forceLayout();
			}
		});

		navigation.handleCurrent();

		RootPanel.get().add(vp);
	}

	private IsWidget mainApp() {
		VerticalLayoutContainer outer = new VerticalLayoutContainer();
		ToolBar toolBar = new ToolBar();
		toolBar.add(new Label("Viola: a fiddle for GWT"));

		toolBar.add(new FillToolItem());

		loginbtn = new TextButton("Login", new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				loginSequence();
			}
		});
		toolBar.add(loginbtn);
		userbtn = new TextButton("");
		userbtn.setMenu(new Menu());
		userbtn.getMenu().add(new MenuItem("Profile", new SelectionHandler<MenuItem>() {
			@Override
			public void onSelection(SelectionEvent<MenuItem> event) {
				ProfileEditorPlace updateProfile = placeManager.create(ProfileEditorPlace.class);
				updateProfile.setId(userId);
				placeManager.submit(updateProfile);
			}
		}));
		userbtn.getMenu().add(new MenuItem("My Projects", new SelectionHandler<MenuItem>() {
			@Override
			public void onSelection(SelectionEvent<MenuItem> event) {
				Window.alert("not implemented");
			}
		}));
		userbtn.getMenu().add(new SeparatorMenuItem());
		userbtn.getMenu().add(new MenuItem("Log out", new SelectionHandler<MenuItem>() {
			@Override
			public void onSelection(SelectionEvent<MenuItem> event) {
				setSessionId(null, null, null, false);
			}
		}));
		userbtn.hide();
		toolBar.add(userbtn);


		outer.add(toolBar, new VerticalLayoutData(1, -1));
		outer.add(container, new VerticalLayoutData(1, 1));
		return outer;
	}

	private void loginSequence() {
		exportAuthSuccess();
		Window.open("https://accounts.google.com/o/oauth2/auth?scope=openid&response_type=code&redirect_uri=http://viola.colinalworth.com/oauth2callback&client_id=888496828889-cjuie9aotun74v1p9tbrb568rchtjkc9.apps.googleusercontent.com&hl=en&from_login=1", "oauth", "");//&approval_prompt=force
	}

	private void setSessionId(String sessionId, String userId, String displayName, boolean newUser) {
		sessionManager.setCurrentSessionId(sessionId);
		userIdManager.setCurrentUserId(userId);
		if (sessionId == null) {
			loginbtn.show();
			userbtn.hide();
			ViolaApp.this.userId = null;
		} else {
			loginbtn.hide();
			userbtn.setText((displayName == null || displayName.isEmpty()) ? userId : displayName);
			userbtn.show();

			if (newUser) {
				ProfileEditorPlace updateProfile = placeManager.create(ProfileEditorPlace.class);
				updateProfile.setId(userId);
				placeManager.submit(updateProfile);
			}
			ViolaApp.this.userId = userId;
		}
		((ToolBar) loginbtn.getParent()).forceLayout();
	}

	private native void exportAuthSuccess() /*-{
		var that = this;
		if (!$wnd.authSuccess) {
			$wnd.authSuccess = $entry(function(sessionId, userId, displayName, newUser) {
				that.@com.colinalworth.gwt.viola.web.client.ViolaApp::setSessionId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)(sessionId, userId, displayName, newUser);
			});
		}
	}-*/;
}
