resource "google_alloydb_cluster" "world_cup_ddd_cloud_run_cluster" {
  project         = var.project_id

  cluster_id = "world-cup-qatar-ddd-cloud-run"
  location   = "europe-west1"
  network_config {
    network = "default"
  }

  initial_user {
    password = "alloydb-cluster"
  }
}

resource "google_alloydb_instance" "world_cup_ddd_cloud_run_primary_instance" {
  cluster       = google_alloydb_cluster.world_cup_ddd_cloud_run_cluster.name
  instance_id   = "primary-instance"
  instance_type = "PRIMARY"

  machine_config {
    cpu_count = 2
  }
}

# resource "google_compute_global_address" "private_ip_alloc" {
#   name          =  "alloydb-cluster"
#   address_type  = "INTERNAL"
#   purpose       = "VPC_PEERING"
#   prefix_length = 16
#   network       = google_compute_network.default.id
# }