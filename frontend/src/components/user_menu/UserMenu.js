import React, {Component} from 'react';
import UserLogin from "./UserLogin";
import AdminMenu from "./AdminMenu";
import LibrarianMenu from "./LibrarianMenu";
import TeacherMenu from "./TeacherMenu";
import StudentMenu from "./StudentMenu";
import DefaultMenu from "./DefaultMenu";
import UserInfo from "./UserInfo";

class UserMenu extends Component {

    constructor(props) {
        super(props);
        this.state = {drawingMenu: <DefaultMenu/>, loginForm: <UserLogin refresh={this.refresh}/>};
    }

    refresh = (data) => {
        this.setState({data: data});
        this.getDrawingMenu();
        console.log('refresh');
    };

    getDrawingMenu = () => {
        let drawingMenu = null;
        let loginForm = null;

        if (window.sessionStorage.getItem('access') === 'true') {
            console.log(window.sessionStorage.getItem('role'));
            loginForm = <UserInfo refresh={this.refresh}/>
            switch (window.sessionStorage.getItem('role')) {
                case 'ADMIN':
                    drawingMenu = <AdminMenu/>;
                    window.sessionStorage.setItem('post', 'Администратор');
                    break;
                case 'LIBRARIAN':
                    drawingMenu = <LibrarianMenu/>;
                    window.sessionStorage.setItem('post', 'Библиотекарь');
                    break;

                case 'TEACHER':
                    drawingMenu = <TeacherMenu/>;
                    window.sessionStorage.setItem('post', 'Преподаватель');
                    break;
                case 'STUDENT':
                    drawingMenu = <StudentMenu/>;
                    window.sessionStorage.setItem('post', 'Студент');
                    break;
                default:
                    drawingMenu = <DefaultMenu/>
            }
        } else {
            loginForm = <UserLogin refresh={this.refresh}/>
            drawingMenu = <DefaultMenu/>
        }

        this.setState({drawingMenu: drawingMenu, loginForm: loginForm})
    };

    render() {
        return (
            <div>
                {this.state.loginForm}
                {this.state.drawingMenu}
            </div>
        );
    }
}

export default UserMenu;
