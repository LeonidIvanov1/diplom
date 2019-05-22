import React, {Component} from 'react';
import {Alert, Button, Container, Form} from "react-bootstrap";

class UserLogin extends Component {

    refresh = this.props.refresh;

    constructor(props) {
        super(props);
        this.state = {
            login: "",
            password: "",
            user_id: "",
            loginAlert: false
        }
    }

    showAlert = () => {
        if (this.state.loginAlert) {
            console.log("не зашел")
            return <div>
                <Alert key={"error"} variant="danger" className="error">
                    Логин и пароль не совпадают
                </Alert>
            </div>
        }
    };

    onLoginClick = (e) => {
        e.preventDefault();
        let data = {
            command: 'login',
            login: this.state.login,
            password: this.state.password
        };
        var url = 'http://localhost:8088/library_war/FrontController?';
        this.sendData(url, data);

    };

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
            }).then(responseJSON => {
            this.setSessionLoginData(responseJSON);
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });


    };


    setSessionLoginData = (data) => {
        if (data.userAccess) {

            window.sessionStorage.setItem('access', data.userAccess);
            window.sessionStorage.setItem('role', data.value.role);
            window.sessionStorage.setItem('login', data.value.login);
            window.sessionStorage.setItem('fullName', data.value.fullName);
            window.sessionStorage.setItem('user_id', data.value.id);
            this.setState({loginAlert: false});
            this.refresh(data.userAccess);
        } else {
            console.log(' ne zashlo');

            this.setState({loginAlert: true});
        }
    };

    render() {

        return (
            <Container className="login-form">
                <Form onSubmit={this.onLoginClick}>
                    <Form.Group controlId="formGroupEmail">
                        <Form.Label>Логин</Form.Label>
                        <Form.Control type="text" value={this.state.login} placeholder="Введите логин" required
                                      size="20" onChange={(e) => {
                            this.setState({login: e.target.value})
                        }}/>
                    </Form.Group>
                    <Form.Group controlId="formGroupPassword">
                        <Form.Label>Пароль</Form.Label>
                        <Form.Control type="password"
                                      placeholder="Введите пароль" value={this.state.password} required size="20"
                                      onChange={(e) => {
                                          this.setState({password: e.target.value})
                                      }}/>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Войти
                    </Button>
                </Form>

                {this.showAlert()}
            </Container>
        );
    }

}

export default UserLogin;
