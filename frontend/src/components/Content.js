import React, {Component} from 'react';
import HomePageContent from "./HomePageContent";
import Container from "react-bootstrap/Container";
import {Row} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import UserMenu from "./UserMenu";
import FAQContent from "./FAQContent";

class Content extends Component {

    render() {
        const page = this.props.page;
        let pageContentElement = <HomePageContent/>;
        switch (page) {
            case 'home':
                pageContentElement = <HomePageContent/>;
                break;
            case 'faq':
                pageContentElement = <FAQContent/>;
                break;
            default:
        }


        return (
            <Container fluid="true">
                <Row>
                    <Col md="3">
                        <UserMenu/>
                    </Col>
                    <Col md="9">
                        {pageContentElement}
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default Content;
