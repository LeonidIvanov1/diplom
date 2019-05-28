import React, {Component} from 'react';
import {Container, ListGroup} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

class TeacherMenu extends Component {
    render() {
        return (
            <Container className="user-menu">

                <ListGroup>
                    <LinkContainer to="/user_library">
                        <ListGroup.Item action>
                            Каталог учебной литературы
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/reserve">
                        <ListGroup.Item action>
                            Резервирование
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/reserve_list">
                        <ListGroup.Item action>
                            Список заявок
                        </ListGroup.Item>
                    </LinkContainer>

                </ListGroup>

            </Container>
        );
    }
}

export default TeacherMenu;
