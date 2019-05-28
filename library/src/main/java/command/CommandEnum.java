package command;

import command.disciplinescommand.*;
import command.groupscommnad.*;
import command.literaturecommand.*;
import command.specialtiescommand.*;
import command.userscommand.*;

/**
 * Command enumeration
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
    GET_USERS {{
        this.command = new GetUsersCommand();
    }},

    GET_USER_COUNT {
        {
            this.command = new GetUserCountCommand();
        }
    },
    GET_GROUPS {
        {
            this.command = new GetGroupsCommand();
        }
    }, ADD_USER {
        {
            this.command = new AddUserCommand();
        }

    }, GET_USER_INFO {
        {
            this.command = new GetUserInfoCommand();
        }
    }, GET_TEACHER_DISCIPLINES {
        {
            this.command = new GetTeacherDisciplinesCommand();
        }
    }, DELETE_USER {
        {
            this.command = new DeleteUserCommand();
        }
    }, CHANGE_USER {
        {
            this.command = new ChangeUserCommand();
        }

    }, GET_GROUPS_COUNT {
        {
            this.command = new GetGroupsCountCommand();
        }
    }, GET_GROUP_INFO {
        {
            this.command = new GetGroupCommand();
        }

    }, ADD_GROUP {
        {
            this.command = new AddGroupCommand();
        }
    }, CHANGE_GROUP {
        {
            this.command = new ChangeGroupCommand();
        }
    }, DELETE_GROUP {
        {
            this.command = new DeleteGroupCommand();
        }
    }, GET_SPECIALTIES {
        {
            this.command = new GetSpecialtiesCommand();
        }
    }, GET_DISCIPLINES {
        {
            this.command = new GetDisciplinesCommand();
        }
    }, GET_DISCIPLINES_ID {
        {
            this.command = new GetDisciplinesIDCommand();
        }

    }, GET_DISCIPLINE {
        {
            this.command = new GetDisciplineCommand();
        }

    }, ADD_DISCIPLINE {
        {
            this.command = new AddDisciplineCommand();
        }
    }, CHANGE_DISCIPLINE {
        {
            this.command = new ChangeDisciplineCommand();
        }
    }, DELETE_DISCIPLINE {
        {
            this.command = new DeleteDisciplineCommand();
        }
    }, GET_SPECIALTY {
        {
            this.command = new GetSpecialtyCommand();
        }

    }, ADD_SPECIALTY {
        {
            this.command = new AddSpecialtyCommand();
        }

    }, CHANGE_SPECIALTY {
        {
            this.command = new ChangeSpecialtyCommand();
        }
    }, DELETE_SPECIALTY {
        {
            this.command = new DeleteSpecialtyCommand();
        }
    }, GET_STUDENT_DATA {
        {
            this.command = new GetStudentData();
        }

    }, GET_BOOK_COLLECTION_COUNT {
        {
            this.command = new GetLiteratureCollectionCountCommand();
        }
    }, GET_BOOK_COLLECTIONS {
        {
            this.command = new GetLiteratureCollectionCommand();
        }
    }, GET_BOOK_COUNT {
        {
            this.command = new GetLiteratureCountCommand();
        }
    }, GET_BOOKS {
        {
            this.command = new GetLiteratureCommand();
        }
    }, GET_BOOK_INFO {
        {
            this.command = new GetBookInfoCommand();
        }
    }, ADD_BOOK {
        {
            this.command = new AddBookCommand();
        }
    }, CHANGE_BOOK {
        {
            this.command = new ChangeBookCommand();
        }
    }, DELETE_BOOK {
        {
            this.command = new DeleteBookCommand();
        }
    }, GET_USER_BOOKS {
        {
            this.command = new GetUserBooksCommand();
        }
    }, GET_BOOKS_DATE {

        {
            this.command = new GetBooksDatesCommand();
        }

    }, RETURN_LITERATURE {
        {
            this.command = new ReturnLiteratureCommand();
        }

    }, EXTEND_LITERATURE {
        {
            this.command = new ExtendLiteratureCommand();
        }
    }, GET_SPECIALTY_DISCIPLINES {
        {
            this.command = new GetSpecialtyDisciplinesCommand();
        }

    }, CHECK_LITERATURE {
        {
            this.command = new CheckLiterature();
        }
    }, GET_DISCIPLINES_GROUP {
        {
            this.command = new GetDisciplineGroupsCommand();
        }
    }, GET_LIBRARY_FUND_INFO {
        {
            this.command = new GetLibraryFundDataCommand();
        }
    }, GET_DEBTOR_STUDENTS {
        {
            this.command = new GetDebtorsStudentsCommand();
        }
    }, GET_TEACHER_GROUPS {
        {
            this.command = new GetTeacherGroupsCommand();
        }
    }, CREATE_RESERVE_REQUEST {
        {
            this.command = new CreateReserveRequest();
        }
    }, GET_RESERVE_LIST {
        {
            this.command = new GetReserveRequestsListCommand();
        }

    }, DELETE_RESERVE_REQUEST {
        {
            this.command = new DeleteReserveRequestCommand();
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