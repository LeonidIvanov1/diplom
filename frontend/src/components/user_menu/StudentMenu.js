import React, {Component} from 'react';
import {Container, ListGroup} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";

class StudentMenu extends Component {
    render() {
        return (
            <Container className="user-menu">

                <ListGroup>
                    <LinkContainer to="/user_library">
                        <ListGroup.Item action>
                            Каталог учебной литературы
                        </ListGroup.Item>
                    </LinkContainer>
                    <LinkContainer to="/taken_literature">
                        <ListGroup.Item action>
                           Список литературы
                        </ListGroup.Item>
                    </LinkContainer>

                </ListGroup>

            </Container>
        );
    }
}

export default StudentMenu;
