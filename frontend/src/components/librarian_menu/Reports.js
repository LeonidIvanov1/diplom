import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Table} from "react-bootstrap";
import Button from "react-bootstrap/es/Button";


class Reports extends Component {




    constructor(props) {

        super(props);
    }


    componentDidMount() {


    }



    render() {
        return (
            <Container fluid="true">
                <Button onClick={() => {
                    this.props.history.push("/fgos");
                }}>Проверка ФГОС</Button>
                <p></p>
                <Button onClick={() => {
                    this.props.history.push("/reports/fund");
                }}>Состояние библиотечного фонда</Button>
                <p></p>
                <Button onClick={() => {
                    this.props.history.push("/reports/debtors");
                }}>Список задолжников</Button>
            </Container>
        );
    }
}

export default Reports;
