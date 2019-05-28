import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Alert, Table} from "react-bootstrap";


class FGOSCheck extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            specialty: "",
            discipline: "",
            literature: "",
            specialties: "",
            result: false

        }
        ;
    }


    componentDidMount() {
        let report = JSON.parse(window.sessionStorage.getItem('fgos'));
        console.log(report);
        this.getSpecialty(report.specialty);
        this.getDiscipline(report.discipline);
        this.getGroups(report.specialty, report.discipline);
        this.setState({literature: JSON.parse(report.literatureCollection), result: report.result})

    }

    getSpecialty(id) {
        let data = {
            command: 'GET_SPECIALTY',
            specialty_id: id
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
                specialty: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getDiscipline(id) {
        let data = {
            command: 'GET_DISCIPLINE',
            discipline_id: id
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
                discipline: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
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


    getGroups(specialtyID, groupID) {
        let data = {
            command: 'GET_DISCIPLINES_GROUP',
            discipline_id: groupID,
            specialty_id: specialtyID
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
                specialties: data.value
            });
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

    getGroupsRows() {
        let groups = this.state.specialties;
        let rows = [];
        for (let i = 0; i < groups.length; i++) {
            let row = <tr>
                <td>{groups[i].name}</td>
                <td>{groups[i].students.length}</td>
                <th></th>
                <th></th>
            </tr>;
            rows.push(row);

        }
        return rows;
    }

    getAllStudentsCount() {
        let count = 0;
        let groups = this.state.specialties;

        for (let i = 0; i < groups.length; i++) {
            count += groups[i].students.length;
        }
        return count;
    }

    getLiteratureRows() {
        console.log(this.state.literature);
        let groups = this.state.literature;
        let rows = [];
        for (let i = 0; i < groups.length; i++) {
            let row = <tr>
                <td>{groups[i].name}</td>
                <td>{groups[i].author}</td>
                <td>{groups[i].year}</td>
                <td>{groups[i].inStockCount}</td>
            </tr>;
            rows.push(row);

        }
        return rows;
    }

    getAllLiteratureCount() {
        let groups = this.state.literature;
        let rows = 0;
        for (let i = 0; i < groups.length; i++) {
            rows += groups[i].inStockCount;
        }
        return rows;
    }

    getResult() {
        if (this.state.result) {
           return <Alert key={"dasda"} variant="success">
                Проверка пройдена успешно.
            </Alert>
        } else {
           return <Alert key={"zzzz"} variant="danger">
                Проверка не пройдена.
            </Alert>
        }
    }

    render() {
        return (
            <Container fluid="true">
                <h2>Отчет по результатам проверки обеспеченности учебного процесса библиотечным фондом согласно
                    ФГОС</h2>
                <Table responsive className="data-table">
                    <tbody>
                    <tr>
                        <th>Специальность/направление</th>
                        <th>Код</th>
                        <th>Коэффициент ФГОС</th>
                        <th></th>
                    </tr>
                    <tr>
                        <td>{this.state.specialty.name}</td>
                        <td>{this.state.specialty.specialtyCode}</td>
                        <td>{this.state.specialty.standardParameter}</td>
                        <th></th>
                    </tr>

                    <tr>
                        <th>Дисциплина</th>
                        <th></th>
                        <th></th>
                        <th></th>

                    </tr>
                    <tr>
                        <td>{this.state.discipline.name}</td>
                        <th></th>
                        <th></th>
                        <th></th>

                    </tr>

                    <tr>
                        <th>Учебная группа</th>
                        <th>Количество учащихся</th>
                        <th></th>
                        <th></th>
                    </tr>
                    {this.getGroupsRows()}
                    <tr>
                        <th>Всего:</th>
                        <td>{this.getAllStudentsCount()}</td>
                        <th></th>
                        <th></th>
                    </tr>
                    <tr>
                        <th>Наименование учебной литературы</th>
                        <th>Автор</th>
                        <th>Год</th>
                        <th>Количество свободных экземпляров</th>
                    </tr>
                    {this.getLiteratureRows()}
                    <tr>
                        <th>Всего:</th>
                        <td>{this.getAllLiteratureCount()}</td>
                        <th></th>
                        <th></th>
                    </tr>

                    </tbody>
                </Table>
                {this.getResult()}
            </Container>
        );
    }
}

export default FGOSCheck;
