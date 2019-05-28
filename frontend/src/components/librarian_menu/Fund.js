import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Table} from "react-bootstrap";
import book_collection from "./Library";


class Fund extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            users_count:"",
            literature:"",
            fundData:""

        }
        ;
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

    componentDidMount() {


       this.getUsersCount();
       this.getLiterature();
       this.getLibraryFundData();
    }

    getLibraryFundData() {
        let data = {
            command: 'GET_LIBRARY_FUND_INFO',
            search_data: "",
            start: 0,
            end: 1000
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({fundData: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }
    getLiterature() {
        let data = {
            command: 'GET_book_collections',
            search_data: "",
            start: 0,
            end: 1000
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({literature: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getUsersCount() {
        let data = {
            command: 'GET_USER_COUNT',
            search_data: ""
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({users_count: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    setBookType(data) {
        switch (data) {
            case 'BOOK':
                return 'Учебное пособие';
            case 'TRAINING':
                return 'Методическое пособие'
        }
    }

    createbook_collectionRows = () => {
        let book_collection = this.state.literature;
        let rows = [];
        if (book_collection != null) {
            for (let i = 0; i < book_collection.length; i++) {
                let row = <tr key={i + book_collection[i].id} id={book_collection[i].groupID}>
                    <td>{book_collection[i].name}</td>
                    <td>{book_collection[i].author}</td>
                    <td>{book_collection[i].year}</td>
                    <td>{this.setBookType(book_collection[i].literatureType)}</td>
                    <td>{book_collection[i].inStockCount}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    };

    render() {
        return (
            <Container fluid="true">
                <h2>Отчет о состоянии библиотечного фонда</h2>
                <Table responsive className="data-table">
                    <tbody>
                    <tr>
                        <th>Список коллекций учебной литературы</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>

                    </tr>
                    <tr>
                        <th>Наименование</th>
                        <th>Автор</th>
                        <th>Год</th>
                        <th>Тип</th>
                        <th>Количество свобоных экземпляров</th>
                    </tr>
                    {this.createbook_collectionRows()}

                    <tr>
                        <th>Итоги</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>

                    </tr>
                    <tr>
                        <th>Всего</th>
                        <th>В наличии</th>
                        <th>Зарезервировано</th>
                        <th>Нет в наличии</th>
                        <th>Списано</th>

                    </tr>

                    <tr>
                        <th>{this.state.fundData.all}</th>
                        <th>{this.state.fundData.inStock}</th>

                        <th>{this.state.fundData.reserved}</th>
                        <th>{this.state.fundData.outOfStock}</th>
                        <th>{this.state.fundData.writtenOff}</th>

                    </tr>

                    </tbody>
                </Table>
            </Container>
        );
    }
}

export default Fund;
