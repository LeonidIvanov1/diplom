import React, {Component} from 'react';
import {Button, Container, Form} from "react-bootstrap";

class UserLogin extends Component {
    render() {
        return (
            <Container className="login-form">
                <Form>
                    <Form.Group controlId="formGroupEmail">
                        <Form.Label>Логин</Form.Label>
                        <Form.Control type="text" placeholder="Введите логин"/>
                    </Form.Group>
                    <Form.Group controlId="formGroupPassword">
                        <Form.Label>Пароль</Form.Label>
                        <Form.Control type="password"
                                      placeholder="Введите пароль"/>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Ввойти
                    </Button>
                </Form>
            </Container>
        );
    }
}

export default UserLogin;
