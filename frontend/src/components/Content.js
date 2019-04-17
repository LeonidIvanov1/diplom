import React, {Component} from 'react';
import HomePageContent from "./HomePageContent";
import Container from "react-bootstrap/Container";
import {Row} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import UserMenu from "./UserMenu";
import FAQContent from "./FAQContent";

class Content extends Component {
    constructor(props) {
        super(props);
        this.state = {page: null};
    }

    onPageChange(page) {
        this.setState({page: page});
    }
    render() {
        const page = this.state.page;
        let pageContentElement = <HomePageContent page={"home"}/>;
        switch (page) {
            case 'home':
                pageContentElement = <HomePageContent page={"home"}/>;
                break;
            case 'faq':
                pageContentElement = <FAQContent page={"faq"}/>;
                break;
            default:
        }


        return (
            <Container fluid="true">
                <Row>
                    <Col md="3">
                        <UserMenu changePage = {this.onPageChange(page)}/>
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
