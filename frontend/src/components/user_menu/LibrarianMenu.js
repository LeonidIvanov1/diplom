import React, {Component} from 'react';
import {Container, ListGroup} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

class LibrarianMenu extends Component {
    render() {
        return (
            <Container className="user-menu">

                <ListGroup>
                    <LinkContainer to="/library">
                        <ListGroup.Item action>
                            Библиотечный фонд
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/fgos">
                        <ListGroup.Item action>
                            Проверка ФГОС
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/readers">
                        <ListGroup.Item action>
                            Читатели
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/repots">
                        <ListGroup.Item action>
                            Отчеты
                        </ListGroup.Item>
                    </LinkContainer>
                </ListGroup>

            </Container>
        );
    }
}

export default LibrarianMenu;
