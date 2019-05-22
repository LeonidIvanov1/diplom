import React, {Component} from 'react';
import {Button, Container, Form} from "react-bootstrap";

class UserInfo extends Component {

    refresh = this.props.refresh;


    sendData = (url, data) => {
        var formData = new FormData();

        for (var name in data) {
            formData.append(name, data[name]);
        }

        const searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: searchParams
        }).then((response) => response.text())
            .then(responce => {
                return JSON.parse(responce);
            }).then(window.sessionStorage.setItem('access', 'false')).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    onLogoutClick = (e) => {
        var data = {
            command: 'logout',
            login: window.sessionStorage.getItem('login')
        };
        var url = 'http://localhost:8088/library_war/FrontController?';
        this.sendData(url, data);
        window.sessionStorage.setItem('access', false);
        this.refresh(false);

    };


    render() {


        return (
            <Container className="login-form">
                <Form>
                    <div className="user-info">
                        Имя: {window.sessionStorage.getItem('fullName')}
                    </div>
                    <div className="user-info">
                        Должность: {window.sessionStorage.getItem('post')}
                    </div>
                    <Button className="logout-button" variant="outline-primary" type="button" size="sm"
                            onClick={this.onLogoutClick}>
                        Выход
                    </Button>
                </Form>
            </Container>
        );
    }

}

export default UserInfo;
