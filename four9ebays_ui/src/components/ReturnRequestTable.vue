
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Return Requests</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalReturnRequests = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalReturnRequests">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add ReturnRequest</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="ReturnRequestId" type="text" placeholder="Enter ReturnRequestId" v-model="returnRequestToAdd.returnRequestId"></base-input>
  <base-input label="RequestDate" type="text" placeholder="Enter RequestDate" v-model="returnRequestToAdd.requestDate"></base-input>
  <base-input label="ApprovalStatus" type="text" placeholder="Enter ApprovalStatus" v-model="returnRequestToAdd.approvalStatus"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="returnRequests" :row-key="record => record.ReturnRequestId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <ReturnRequestPictureView :returnRequests="returnRequests" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import ReturnRequestService from "../services/ReturnRequestService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import ReturnRequestPictureView from './ReturnRequestPictureView.vue';


const returnRequestsColumns = [
  "returnRequestId",
  "year",
  "date",
  "competitionId",
  "returnRequestId"
]

export default {
  props: {
    returnRequests: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    ReturnRequestPictureView
  },

  data() {
    return {
      modalReturnRequests: false,
        isTableView: true,

      columns: [
        {
          title: 'Return Request Id',
		dataIndex: 'returnRequestId',
          visible: true,
          scopedSlots: { customRender: 'returnRequestId' },
          sorter: true
          //sorter: (a, b) => a.returnRequestId - b.returnRequestId,
          //sorter: (a, b) => a.returnRequestId.localeCompare(b.returnRequestId),
        },
        {
          title: 'Request Date',
		dataIndex: 'requestDate',
          visible: true,
          scopedSlots: { customRender: 'requestDate' },
          sorter: true
          //sorter: (a, b) => a.requestDate - b.requestDate,
          //sorter: (a, b) => a.requestDate.localeCompare(b.requestDate),
        },
        {
          title: 'Approval Status',
		dataIndex: 'approvalStatus',
          visible: true,
          scopedSlots: { customRender: 'approvalStatus' },
          sorter: true
          //sorter: (a, b) => a.approvalStatus - b.approvalStatus,
          //sorter: (a, b) => a.approvalStatus.localeCompare(b.approvalStatus),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} returnRequests`,
      },

      returnRequests: [],
      returnRequestToAdd: {},

      returnRequestsTable: {
        columns: [...returnRequestsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'returnRequestId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderReturnRequestsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let returnRequestsTableData = [];
      for (let i = 0; i < this.returnRequests.length; i++) {
        returnRequestsTableData.push({
          id: i,
          returnRequestId: this.returnRequests[i].returnRequestId,
          year: this.returnRequests[i].year,
          date: this.returnRequests[i].date,
          competitionId: this.returnRequests[i].competitionId,
          returnRequestId: this.returnRequests[i].returnRequestId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-return-requests',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToUserDetail(id) {
      this.$router.push({ name: 'UserDetail', params: { userId: id.toString() }})
    },
    routingToItemDetail(id) {
      this.$router.push({ name: 'ItemDetail', params: { itemId: id.toString() }})
    },
    routingToCategoryDetail(id) {
      this.$router.push({ name: 'CategoryDetail', params: { categoryId: id.toString() }})
    },
    routingToAuctionDetail(id) {
      this.$router.push({ name: 'AuctionDetail', params: { auctionId: id.toString() }})
    },
    routingToBidDetail(id) {
      this.$router.push({ name: 'BidDetail', params: { bidId: id.toString() }})
    },
    routingToOrderDetail(id) {
      this.$router.push({ name: 'OrderDetail', params: { orderId: id.toString() }})
    },
    routingToPaymentDetail(id) {
      this.$router.push({ name: 'PaymentDetail', params: { paymentId: id.toString() }})
    },
    routingToFeedbackDetail(id) {
      this.$router.push({ name: 'FeedbackDetail', params: { feedbackId: id.toString() }})
    },
    routingToWishlistDetail(id) {
      this.$router.push({ name: 'WishlistDetail', params: { wishlistId: id.toString() }})
    },
    routingToNotificationDetail(id) {
      this.$router.push({ name: 'NotificationDetail', params: { notificationId: id.toString() }})
    },
    routingToAddressDetail(id) {
      this.$router.push({ name: 'AddressDetail', params: { addressId: id.toString() }})
    },
    routingToTransactionDetail(id) {
      this.$router.push({ name: 'TransactionDetail', params: { transactionId: id.toString() }})
    },
    routingToReviewDetail(id) {
      this.$router.push({ name: 'ReviewDetail', params: { reviewId: id.toString() }})
    },
    routingToCartDetail(id) {
      this.$router.push({ name: 'CartDetail', params: { cartId: id.toString() }})
    },
    routingToCartItemDetail(id) {
      this.$router.push({ name: 'CartItemDetail', params: { cartItemId: id.toString() }})
    },
    routingToImageDetail(id) {
      this.$router.push({ name: 'ImageDetail', params: { imageId: id.toString() }})
    },
    routingToReturnRequestDetail(id) {
      this.$router.push({ name: 'ReturnRequestDetail', params: { returnRequestId: id.toString() }})
    },
    routingToMessageDetail(id) {
      this.$router.push({ name: 'MessageDetail', params: { messageId: id.toString() }})
    },
    routingToSubscriptionDetail(id) {
      this.$router.push({ name: 'SubscriptionDetail', params: { subscriptionId: id.toString() }})
    },
    routingToPromotionDetail(id) {
      this.$router.push({ name: 'PromotionDetail', params: { promotionId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForReturnRequestsChanged', this.searchQuery);
		//this.renderReturnRequestsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalReturnRequests = false;

      const currentDate = new Date().getTime();
      this.returnRequestToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.returnRequestToAdd);
      console.log(jsonData);
      
      const res = await ReturnRequestService.addReturnRequest(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderReturnRequestsTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
