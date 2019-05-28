import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Table} from "react-bootstrap";
import book_collection from "./Library";


class Debtors extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            students: "",
            bookDate: [],
            books:[]
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
        this.getDebtorsStudents();

    }


    getBooksHistory(userID) {
        let data = {
            command: 'GET_BOOKS_DATE',
            user_id: userID,
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
            let arr = this.state.bookDate;
            let newVal = {user: userID, data: data.value};
            arr.push(newVal);
            this.setState({
                bookDate: arr
            });
            this.createDebList();

        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getDebtors() {
        for (let i = 0; i < this.state.students.length; i++) {
            this.getBook(this.state.students[i].id);
            this.getBook(this.state.students[i].id);
        }
    }

    getUsers(id) {

    }
    createDebList() {
        for (let i = 0; i < this.state.bookDate.length; i++) {
            console.log(this.state.bookDate[i].data[i].id);
        }
        for (let i = 0; i < this.state.bookDate.length; i++) {
            console.log(this.state.bookDate[i].user);
        }
    }

    getBook(id) {
        let data = {
            command: 'GET_BOOK_INFO',
            book_id: id,
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
            let arr = this.state.books;
            arr.push(data.value);
            this.setState({
                books: arr
            });

        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getDebtorsStudents() {
        let data = {
            command: 'GET_DEBTOR_STUDENTS',
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
            this.setState({students: data.value});
            this.getDebtors();
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    render() {
        return (
            <Container fluid="true">
                <h2>Список задолжников</h2>
                <Table responsive className="data-table">
                    <tbody>
                    <tr>
                        <th>Группа</th>
                        <th>ФИО</th>
                    </tr>
                    </tbody>
                </Table>
            </Container>
        );
    }
}

export default Debtors;
