$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2014-08',
            log_count: 170304
        }, {
            period: '2014-09',
            log_count: 140304
        }, {
            period: '2014-10',
            log_count: 190304
        }, {
            period: '2014-11',
            log_count: 180309
        }, {
            period: '2014-12',
            log_count: 160304
        }, {
            period: '2015-01',
            log_count: 170304
        }, {
            period: '2015-02',
            log_count: 160304
        }, {
            period: '2015-03',
            log_count: 150304
        }, {
            period: '2015-04',
            log_count: 90304
        }, {
            period: '2015-05',
            log_count: 110304
        }, {
            period: '2015-06',
            log_count: 180304
        }, {
            period: '2015-07',
            log_count: 130304
        }],
        xkey: 'period',
        ykeys: ['log_count'],
        labels: ['log_count'],
        pointSize: 4,
        hideHover: 'auto',
        resize: true
    });

    

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2006',
            a: 100,
            b: 90
        }, {
            y: '2007',
            a: 75,
            b: 65
        }, {
            y: '2008',
            a: 50,
            b: 40
        }, {
            y: '2009',
            a: 75,
            b: 65
        }, {
            y: '2010',
            a: 50,
            b: 40
        }, {
            y: '2011',
            a: 75,
            b: 65
        }, {
            y: '2012',
            a: 100,
            b: 90
        }],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Series A', 'Series B'],
        hideHover: 'auto',
        resize: true
    });

});
