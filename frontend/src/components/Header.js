import React, {Component} from 'react';
import {Container, Nav, Navbar} from "react-bootstrap";
import {LinkContainer} from 'react-router-bootstrap'

class Header extends Component {


    render() {

        return (
            <Container fluid={true}>
                <Navbar collapseOnSelect expand="lg" bg="light" variant="light" fixed="top">
                    <LinkContainer to="/">
                        <Navbar.Brand>Главная</Navbar.Brand>
                    </LinkContainer>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                            <LinkContainer to="/faq">
                                <Nav.Link>Справка</Nav.Link>
                            </LinkContainer>
                            <LinkContainer to="/contacts">
                                <Nav.Link href="/contacts">Контакты</Nav.Link>
                            </LinkContainer>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </Container>
        );
    }
}

export default Header;
