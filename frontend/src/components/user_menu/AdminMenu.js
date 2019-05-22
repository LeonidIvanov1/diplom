import React, {Component} from 'react';
import {Container, ListGroup} from "react-bootstrap";
import {LinkContainer} from 'react-router-bootstrap'

class AdminMenu extends Component {
    render() {
        return (
            <Container className="user-menu">

                <ListGroup>
                    <LinkContainer to="/users">
                        <ListGroup.Item action>
                            Пользователи
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/students_groups">
                        <ListGroup.Item action>
                            Студенческие группы
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/disciplines">
                        <ListGroup.Item action>
                            Дисциплины
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/specialties">
                        <ListGroup.Item action>
                            Специальности
                        </ListGroup.Item>
                    </LinkContainer>
                </ListGroup>

            </Container>
        );
    }
}

export default AdminMenu;
