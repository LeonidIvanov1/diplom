import React, {Component} from 'react';
import {Container, Nav, Navbar} from "react-bootstrap";

class Header extends Component {


    render() {
        console.log();
        return (
            <Container fluid={true}>
                <Navbar collapseOnSelect expand="lg" bg="light" variant="light" fixed="top">
                    <Navbar.Brand href="#home" onClick={e => {
                        e.preventDefault();
                        this.props.pageChange("home")
                    }}>Главная</Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link href="#features" onClick={e => {
                                e.preventDefault();
                                this.props.pageChange("faq")
                            }}>Справка</Nav.Link>
                            <Nav.Link href="#pricing" onClick={e => {
                                e.preventDefault();
                                this.props.pageChange("contact")
                            }}>Контакты</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </Container>
        );
    }
}

export default Header;
