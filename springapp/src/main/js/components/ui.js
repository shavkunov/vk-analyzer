import React from 'react';
import StartForm from './start-form'
import StatsView from './stats-view'
import Paper from 'material-ui/Paper';
import Snackbar from 'material-ui/Snackbar';
import RaisedButton from 'material-ui/RaisedButton';
import Loading from 'react-loading-animation';

const paper = {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    marginTop: "15px",
    width: "90%",
};

const center = {
    display: "flex",
    flexDirection: "column",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
};

class UI extends React.Component {
    constructor() {
        super();

        this.state = {
            open: false,
            beginLoad: false,
            isTableLoaded: false,
            table: null
        }
    }

    renderLoading = () => {
        if (this.state.beginLoad) {
            return (
                <Loading style={{ marginTop: "40px" }} />
            );
        }
    };

    renderTable = () => {
        if (this.state.isTableLoaded) {
            return (
                <Paper zDepth={4} style={paper} >

                    <RaisedButton onClick={this.handleTouchTap}
                                  label="Add to database"/>

                    <StatsView table={this.state.table}/>

                    <Snackbar contentStyle={center}
                              open={this.state.open}
                              message="Stats saved"
                              autoHideDuration={4000}
                              onRequestClose={this.handleRequestClose}
                    />
                </Paper>
            );
        }
    };

    handleTouchTap = () => {
        this.setState({
            open: true,
        });
    };

    handleRequestClose = () => {
        this.setState({
            open: false,
        });
    };

    render() {
        return (
            <div style={center}>
                <StartForm handleSubmit={this.handleSubmit.bind(this)}
                           beginLoad={this.beginLoad.bind(this)}
                           stopLoad={this.stopLoad.bind(this)}/>
                {this.renderLoading()}
                {this.renderTable()}
            </div>
        );
    }

    beginLoad = () => {
        this.setState({
            beginLoad: true,
        });
    };

    stopLoad = () => {
        this.setState({
            beginLoad: false,
        });
    };


    handleSubmit = (table) => {
        this.stopLoad();
        this.setState({
            isTableLoaded: true,
            table: table,
        });
    };
}

export default UI;