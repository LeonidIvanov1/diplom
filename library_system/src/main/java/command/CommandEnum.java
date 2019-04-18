package command;

/**
 * 
 * Command enumeration
 *
 */
public enum CommandEnum {
	/**
	 * Login command
	 */
	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	/**
	 * Logout command
	 */
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	/**
	 * Get users command
	 */
	GET_USERS {
		{
			this.command = new GetUsersCommand();
		}
	},
	/**
	 * Get user info command
	 */
	GETUSERINFO {
		{
			this.command = new GetUserInfoCommand();
		}
	},
	DELETE_USER {
		{
			this.command = new DeleteUserCommand();
		}
	},
	CHANGE_USER {
		{
			this.command = new ChangeUserInfoCommand();
		}
	},
	GET_GROUPS {
		{
			this.command = new GetGroupsCommand();
		}

	},
	ADD_USER {
		{
			this.command = new AddUserCommand();
		}
	},
	GET_ONLINE_USERS {
		{
			this.command = new GetOnlineUsers();
		}
	},
	SEARCH_USERS {
		{
			this.command = new SearchUsersCommand();
		}
	},
	FORWARD_TO_ADD_USER {
		{
			this.command = new ForwardToAddUserCommand();
		}
	},
	FORWARD_TO_ADD_GROUP {
		{
			this.command = new ForwardToAddGroupCommand();
		}
	},
	ADD_GROUP {
		{
			this.command = new AddGroupCommand();
		}
	},
	GET_GROUP_INFO {
		{
			this.command = new GetGroupInfoCommand();
		}
	},
	DELETE_GROUP {
		{
			this.command = new DeleteGroupCommand();
		}
	},
	CHANGE_GROUP {
		{
			this.command = new ChangeGroupInfoCommand();
		}
	},
	SEARCH_GROUPS {
		{
			this.command = new SearchGroupsCommand();
		}
	},
	GET_SPECIALTIES {
		{
			this.command = new GetSpecialtiesCommand();
		}
	},
	SEARCH_SPECIALTIES {
		{
			this.command = new SearchSpecialtiesCommand();
		}
	},
	FORWARD_TO_ADD_SPECIALTY {
		{
			this.command = new ForwardToAddSpecialtyCommand();
		}
	},
	GET_SPECIALTY_INFO {
		{
			this.command = new GetSpecialtyInfoCommand();
		}
	},
	DELETE_SPECIALTY {
		{
			this.command = new DeleteSpecialtyCommand();
		}
	},
	ADD_SPECIALTY {
		{
			this.command = new AddSpecialtyCommand();
		}
	},
	GET_LITERATURECOLLECTION {
		{
			this.command = new GetLiteratureCollectionsCommand();
		}
	},
	SEARCH_COLLECTION_LITERATURE {
		{
			this.command = new SearchLiteratureCollectionsCommand();
		}
	},
	SEARCH_LITERATURE {
		{
			this.command = new SearchLiteratureCommand();
		}
	},
	FORWARD_TO_RESERVE_LITERATURE {
		{
			this.command = new ForwardToReserveLiteratureCommand();
		}
	},
	RESERVE_LITERATURE {
		{
			this.command = new ReserveLiteratureCommand();
		}
	},
	FORWARD_TO_ADD_LITERATURE {
		{
			this.command = new ForwardToAddLiteratureCommand();
		}
	},
	ADD_LITERATURE {
		{
			this.command = new AddLiteratureCommand();
		}
	},
	GET_LITERATURE_INFO {
		{
			this.command = new GetLiteratureInfoCommand();
		}
	},
	DELETE_LITERATURE {
		{
			this.command = new DeleteLiteratureCommand();
		}
	},
	CHANGE_LITERATURE {
		{
			this.command = new ChangeLiteratureCommand();
		}
	},
	FORWARD_TO_CHECK_LITERATURE {
		{
			this.command = new ForwardToCheckLiterature();
		}
	},
	CHECK_LITERATURE {
		{
			this.command = new CheckLiteratureCommand();
		}
	},
	FORWARD_TO_MAIN_PAGE {
		{
			this.command = new ForwardToMainPageCommand();
		}
	};
	protected ActionCommand command;

	/**
	 * Return current command
	 * 
	 * @return current command
	 */
	public ActionCommand getCurrentCommand() {
		return command;
	}
}