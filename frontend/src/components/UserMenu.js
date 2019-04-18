import React, {Component} from 'react';
import UserLogin from "./UserLogin";

class UserMenu extends Component {
    render() {
        return (
            <div>
                <UserLogin/>
                <div className="user-menu">
                    Для взаимодействия с информационной системой требуется авторизация. Войдите пожалуйста.
                </div>
            </div>
        );
    }
}

export default UserMenu;
