<template>

    <v-card outlined>
        <v-card-title>
            OrderHistory # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="OrderId" v-model="item.orderId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="StoreId" v-model="item.storeId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="TotalAmount" v-model="item.totalAmount" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="RiderId" v-model="item.riderId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'OrderHistoryViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/orderHistories/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

