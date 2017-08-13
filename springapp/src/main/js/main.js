import React from 'react';
import ReactDOM from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import StartForm from './start-form.js';

const App = () => (
    <MuiThemeProvider>
        <StartForm />
    </MuiThemeProvider>
);

ReactDOM.render(
    <App />,
    document.getElementById('react')
);