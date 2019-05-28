import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Button, ButtonGroup, Col, Form, FormControl, InputGroup, Row, Table} from "react-bootstrap";
import ReactPaginate from 'react-paginate';
import {LinkContainer} from "react-router-bootstrap";
import ReserveRequest from "./ReserveRequest";


class ReserveRequestList extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            reserveRequests: []
        }
        ;
    }


    componentDidMount() {
        this.getReserveRequests();
    }

    getRequestData(data) {
        var formData = new FormData();
        for (var name in data) {
            formData.append(name, data[name]);
        }
        let searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
        return searchParams;
    }

    getReserveRequests() {
        let data = {
            command: 'GET_RESERVE_LIST',
            user_id: window.sessionStorage.getItem("user_id")
        };

        let requestData = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: requestData
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({
                reserveRequests: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getSelectedLiterature() {
        let rows = [];
        let req = this.state.reserveRequests;
        for (let i = 0; i < req.length; i++) {
            let row = <tr>
                <td>{i + 1}</td>
                <td>{req[i].group.name}</td>
                <td>{req[i].discipline.name}</td>
                <td>{req[i].discipline.name}</td>
                <td>{req[i].literatureList[0].name}</td>
                <td>{req[i].literatureList[0].author}</td>
                <td>{req[i].literatureList[0].year}</td>
                <td>{req[i].literatureList[0].literatureType}</td>
                <td><Button>Отменить</Button></td>
            </tr>;
            rows.push(row)
        }
        return rows;
    }

    render() {
        return (
            <Container fluid="true">
                <p>Выбранные экземпляры для проверки</p>
                <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                    <tr>
                        <th>#</th>
                        <th>Группа</th>
                        <th>Дисциплина</th>
                        <th>Название</th>
                        <th>Автор</th>
                        <th>Год</th>
                        <th>Тип</th>
                    </tr>

                    <tbody>
                    {this.getSelectedLiterature()}
                    </tbody>
                </Table>
            </Container>
        );
    }
}

export default ReserveRequestList;
