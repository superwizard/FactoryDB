/**
 * TCSS 491 SPRING 2015
 * Mickey Johnson
 * Final DB project
 */


package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import main.Driver;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;

import java.sql.ResultSet;
import java.util.GregorianCalendar;

import javax.swing.JTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;

import javax.swing.JTextArea;

/**
 * This code was automatically created using the windowbuilder extension in eclipse.
 * @author Mickey Johnson
 *
 */
public class GUImain {

	private JFrame frmDbApp;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;

	/**
	 * Launch the application.
	 */
	public static void start () {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUImain window = new GUImain();
					window.frmDbApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUImain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDbApp = new JFrame();
		frmDbApp.setTitle("DB APP");
		frmDbApp.setMinimumSize(new Dimension(600, 400));
		frmDbApp.setBounds(100, 100, 450, 300);
		frmDbApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDbApp.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmDbApp.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("view orders", null, scrollPane, null);

		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Order Number");
		ResultSet res = Driver.getJobInfo();
		for (int i = 0; i < Driver.getNumJobs(); i++) {
			try {
				res.next();
				if (res.getDate("DateShipped") == null) {
					int order = res.getInt("OrderNumber");
					DefaultMutableTreeNode current = new DefaultMutableTreeNode(order);
					DefaultMutableTreeNode customer = new DefaultMutableTreeNode("Customer: " + res.getString("Customer") + ", Date Required: " + res.getDate("DateRequired"));
					
					ResultSet prods = Driver.getProductionOrder(order);
					while (prods.next()) {
						DefaultMutableTreeNode prod = new DefaultMutableTreeNode("Produce " + prods.getInt("Num") + " of " + prods.getString("Descr"));
						customer.add(prod);
					}
					current.add(customer);
					root.add(current);
				} else {
					i--;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}		
		
		JTree tree = new JTree(root);
		scrollPane.setViewportView(tree);
		textField_2 = new JTextField();
		JPanel panel = new JPanel();
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				// next order number loaded automatically.
				
				textField_2.setText(Integer.valueOf(Driver.getNextJobNumber()).toString());
			}
		});
		tabbedPane.addTab("create order", null, panel, null);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("min(100dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblJobNumber = new JLabel("Order Number:");
		panel.add(lblJobNumber, "4, 2, right, default");
		
		
		textField_2.setEditable(false);
		panel.add(textField_2, "6, 2, fill, default");
		textField_2.setColumns(10);
		
		JLabel lblCustomerName = new JLabel("Customer Name:");
		panel.add(lblCustomerName, "4, 4, right, default");
		
		textField = new JTextField();
		panel.add(textField, "6, 4, fill, default");
		textField.setColumns(10);
		
		JLabel lblDateRequired = new JLabel("Date Required (yyyymmdd):");
		panel.add(lblDateRequired, "4, 6, right, default");
		
		textField_1 = new JTextField();
		panel.add(textField_1, "6, 6, fill, default");
		textField_1.setColumns(10);
		
		JButton button_1 = new JButton("Create Order");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					String yearText = textField_1.getText();
					String custText = textField.getText();
					if (custText.length() > 1) {
						if (yearText.length() == 8) {
							int year = Integer.parseInt(yearText.substring(0, 4));
							int month = Integer.parseInt(yearText.substring(4, 6));
							int day = Integer.parseInt(yearText.substring(6, 8));
							if (year > 0 && month > 0 && day > 0) {
								GregorianCalendar test = new GregorianCalendar();
								test.set(year, month - 1, day);
								if (test.compareTo(new GregorianCalendar())  > 0) {
									Driver.createNewOrder(Integer.parseInt(textField_2.getText()), custText, Integer.parseInt(yearText));
									textField_2.setText(Integer.valueOf(Driver.getNextJobNumber()).toString());
									textField.setText("");
									textField_1.setText("");
								} else {
									JOptionPane.showMessageDialog(frmDbApp, "Required Date must be a future date.");
								}
							} else {
								JOptionPane.showMessageDialog(frmDbApp, "Date not entered correctly.\nMust be in YYYYMMDD format.");
							}
						} else {
							JOptionPane.showMessageDialog(frmDbApp, "Date not entered correctly.\nMust be in YYYYMMDD format.");
						}
					} else {
						JOptionPane.showMessageDialog(frmDbApp, "Please enter customer name.");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel.add(button_1, "4, 8");
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("add product to order", null, panel_2, null);
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOrderNumber = new JLabel("Order Number:");
		panel_2.add(lblOrderNumber, "4, 2, right, default");
		
		textField_5 = new JTextField();
		panel_2.add(textField_5, "6, 2, fill, default");
		textField_5.setColumns(10);
		
		JLabel lblProductNumber = new JLabel("Product Number:");
		panel_2.add(lblProductNumber, "4, 4, right, default");
		
		textField_6 = new JTextField();
		panel_2.add(textField_6, "6, 4, fill, default");
		textField_6.setColumns(10);
		
		JLabel lblNumberToProduce = new JLabel("Number to Produce:");
		panel_2.add(lblNumberToProduce, "4, 6, right, default");
		
		textField_7 = new JTextField();
		panel_2.add(textField_7, "6, 6, fill, default");
		textField_7.setColumns(10);
		
		JButton btnAddProduct = new JButton("Add product");
		btnAddProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					String orderText = textField_5.getText();
					String productText = textField_6.getText();
					String numberText = textField_7.getText();
					if (orderText.length() > 0) {
						if (productText.length() > 0) {
							if (numberText.length() > 0) {
								int order = Integer.parseInt(orderText);
								int product = Integer.parseInt(productText);
								int number = Integer.parseInt(numberText);
								if (Driver.checkValidOrder(order)) {
									if (Driver.checkValidProduct(product)) {
										Driver.createNewProductOrder(order, product, number);
										textField_5.setText("");
										textField_6.setText("");
										textField_7.setText("");
									} else {
										JOptionPane.showMessageDialog(frmDbApp, "Invalid product number.");
									}
								} else {
									JOptionPane.showMessageDialog(frmDbApp, "Invalid order number.");
								}
							} else {
								JOptionPane.showMessageDialog(frmDbApp, "Please enter number to produce.");
							}
						} else {
							JOptionPane.showMessageDialog(frmDbApp, "Please enter product number.");
						}
					} else {
						JOptionPane.showMessageDialog(frmDbApp, "Please enter order number.");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel_2.add(btnAddProduct, "4, 8");
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("complete order", null, panel_1, null);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSelectOrder = new JLabel("Order Number:");
		panel_1.add(lblSelectOrder, "4, 2, right, default");
		
		textField_3 = new JTextField();
		panel_1.add(textField_3, "6, 2, fill, default");
		textField_3.setColumns(10);
		
		JLabel lblShipmentDate = new JLabel("Shipment Date");
		panel_1.add(lblShipmentDate, "4, 4, right, default");
		
		textField_4 = new JTextField();
		panel_1.add(textField_4, "6, 4, fill, default");
		textField_4.setColumns(10);
		
		JButton btnCompleteOrder = new JButton("Complete Order");
		btnCompleteOrder.addMouseListener(new MouseAdapter() {
			
				@Override
				public void mousePressed(MouseEvent e) {
					try {
						String orderText = textField_3.getText();
						if (orderText.length() > 0) {
							int order = Integer.parseInt(orderText);
							String yearText = textField_4.getText();
							if (Driver.checkValidOrder(order)) {
									if (yearText.length() == 8) {
										int year = Integer.parseInt(yearText.substring(0, 4));
										int month = Integer.parseInt(yearText.substring(4, 6));
										int day = Integer.parseInt(yearText.substring(6, 8));
										if (year > 0 && month > 0 && day > 0) {
											GregorianCalendar test = new GregorianCalendar();
											test.set(year, month - 1, day);
											if (test.compareTo(new GregorianCalendar())  <= 0) {
												Driver.completeOrder(order, Integer.parseInt(yearText));
												textField_3.setText("");
												textField_4.setText("");
											} else {
												JOptionPane.showMessageDialog(frmDbApp, "Shipment Date must not be a future date.");
											}
										} else {
											JOptionPane.showMessageDialog(frmDbApp, "Year not entered correctly.\nMust be in YYYYMMDD format.");
										}
									} else {
										JOptionPane.showMessageDialog(frmDbApp, "Year not entered correctly.\nMust be in YYYYMMDD format.");
									}
							} else {
								JOptionPane.showMessageDialog(frmDbApp, "Invalid order number");
							}
						} else {
							JOptionPane.showMessageDialog(frmDbApp, "Please enter order number.");
						}
					} catch (Exception err) {
						JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
					}
				}

		});
		panel_1.add(btnCompleteOrder, "4, 6");
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("create product", null, panel_3, null);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:50dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Part Number:");
		panel_3.add(lblNewLabel, "4, 2, right, default");
		
		textField_8 = new JTextField();
		textField_8.setText(Integer.valueOf(Driver.getNextProductNumber()).toString());
		panel_3.add(textField_8, "6, 2, fill, default");
		textField_8.setColumns(10);
		
		JLabel lblProductionTimein = new JLabel("Production Time (in minutes):");
		panel_3.add(lblProductionTimein, "4, 4, right, default");
		
		textField_9 = new JTextField();
		panel_3.add(textField_9, "6, 4, fill, default");
		textField_9.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		panel_3.add(lblDescription, "4, 6");
		
		JTextArea textArea = new JTextArea();
		panel_3.add(textArea, "6, 6, fill, fill");
		
		JButton btnAddProduct_1 = new JButton("Add Product");
		btnAddProduct_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					String partText = textField_8.getText();
					String productionText = textField_9.getText();
					String descText = textArea.getText();
					if (partText.length() > 0 && productionText.length() > 0 && descText.length() > 0) {
						int part = Integer.parseInt(partText);
						int prod = Integer.parseInt(productionText);
						Driver.createNewProduct(part, prod, descText);
						textField_8.setText(Integer.valueOf(Driver.getNextProductNumber()).toString());
						textField_9.setText("");
						textArea.setText("");
					} else if (productionText.length() <= 0) {
						JOptionPane.showMessageDialog(frmDbApp, "Please enter production time.");
					} else if (descText.length() <= 0) {
						JOptionPane.showMessageDialog(frmDbApp, "Please enter description.");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel_3.add(btnAddProduct_1, "4, 8");
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("add part to product", null, panel_4, null);
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblProductNumber_1 = new JLabel("product number:");
		panel_4.add(lblProductNumber_1, "4, 4, right, default");
		
		textField_10 = new JTextField();
		panel_4.add(textField_10, "6, 4, fill, default");
		textField_10.setColumns(10);
		
		JLabel lblPartNumber = new JLabel("part number:");
		panel_4.add(lblPartNumber, "4, 6, right, default");
		
		textField_11 = new JTextField();
		panel_4.add(textField_11, "6, 6, fill, default");
		textField_11.setColumns(10);
		
		JLabel lblNumberRequired = new JLabel("number required:");
		panel_4.add(lblNumberRequired, "4, 8, right, default");
		
		textField_12 = new JTextField();
		panel_4.add(textField_12, "6, 8, fill, default");
		textField_12.setColumns(10);
		
		JButton btnAddPart = new JButton("add part");
		btnAddPart.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					String prodText = textField_10.getText();
					String partText = textField_11.getText();
					String numbText = textField_12.getText();
					if (prodText.length() <= 0 || !Driver.checkValidProduct(Integer.parseInt(prodText))) {
						JOptionPane.showMessageDialog(frmDbApp, "Invalid product number.");
					} else if (partText.length() <= 0 || !Driver.checkValidPart(Integer.parseInt(partText))) {
						JOptionPane.showMessageDialog(frmDbApp, "Invalid part number.");
					} else if (numbText.length() <= 0) {
						JOptionPane.showMessageDialog(frmDbApp, "Invalid number required.");
					} else {
						int prod = Integer.parseInt(prodText);
						int part = Integer.parseInt(partText);
						int num = Integer.parseInt(numbText);
						Driver.createNewProductPart(prod, part, num);
						textField_10.setText("");
						textField_11.setText("");
						textField_12.setText("");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel_4.add(btnAddPart, "4, 10");
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("create part", null, panel_5, null);
		panel_5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:50dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblPartNumber_1 = new JLabel("Part Number:");
		panel_5.add(lblPartNumber_1, "4, 4, right, default");
		
		textField_13 = new JTextField();
		textField_13.setText(Integer.valueOf(Driver.getNextPartNumber()).toString());
		panel_5.add(textField_13, "6, 4, fill, default");
		textField_13.setColumns(10);
		
		JLabel lblDescription_1 = new JLabel("Description:");
		panel_5.add(lblDescription_1, "4, 6");
		
		JTextArea textArea_1 = new JTextArea();
		panel_5.add(textArea_1, "6, 6, fill, fill");
		
		JButton btnCreatePart = new JButton("create part");
		btnCreatePart.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					String partText = textField_13.getText();
					String descText = textArea_1.getText();
					if (descText.length() <= 0) {
						JOptionPane.showMessageDialog(frmDbApp, "Please enter description.");
					} else {
						Driver.createNewPart(Integer.parseInt(partText), descText);
						textField_13.setText(Integer.valueOf(Driver.getNextPartNumber()).toString());
						textArea_1.setText("");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel_5.add(btnCreatePart, "4, 8");
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane.addTab("part inventory", null, scrollPane2, null);

		
		DefaultMutableTreeNode root2 = new DefaultMutableTreeNode("Part Number");
		for (int i = 1; i < Driver.getNumParts(); i++) {
			DefaultMutableTreeNode current = new DefaultMutableTreeNode(i);
			try {
				DefaultMutableTreeNode info = new DefaultMutableTreeNode("In Stock: " + Driver.getPartInventory(i));
				current.add(info);
				ResultSet orders = Driver.getPartOrders(i);
				if (orders != null) {
					while(orders.next()) {
						DefaultMutableTreeNode order = new DefaultMutableTreeNode("Order Number: " + orders.getInt("OrderNumber") + ", Number Ordered: " + orders.getInt("NumberOrdered") + ", Delivery Date: " + orders.getDate("DeliveryDate"));
						current.add(order);
					}
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			root2.add(current);
		}		
		
		JTree tree2 = new JTree(root2);
		scrollPane2.setViewportView(tree2);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("add part order", null, panel_6, null);
		panel_6.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblOrderNumber_1 = new JLabel("Order Number:");
		panel_6.add(lblOrderNumber_1, "4, 4, right, default");
		
		textField_14 = new JTextField();
		panel_6.add(textField_14, "6, 4, fill, default");
		textField_14.setColumns(10);
		
		JLabel lblPartNumber_2 = new JLabel("Part Number:");
		panel_6.add(lblPartNumber_2, "4, 6, right, default");
		
		textField_15 = new JTextField();
		panel_6.add(textField_15, "6, 6, fill, default");
		textField_15.setColumns(10);
		
		JLabel lblNumberOrdered = new JLabel("Number Ordered:");
		panel_6.add(lblNumberOrdered, "4, 8, right, default");
		
		textField_16 = new JTextField();
		panel_6.add(textField_16, "6, 8, fill, default");
		textField_16.setColumns(10);
		
		JLabel lblDeliveryDate = new JLabel("Delivery Date:");
		panel_6.add(lblDeliveryDate, "4, 10, right, default");
		
		textField_17 = new JTextField();
		panel_6.add(textField_17, "6, 10, fill, default");
		textField_17.setColumns(10);
		
		JButton btnAddOrder = new JButton("Add order");
		btnAddOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					String orderText = textField_14.getText();
					String partText = textField_15.getText();
					String numbText = textField_16.getText();
					String delivText = textField_17.getText();
					if (orderText.length() < 1) {
						JOptionPane.showMessageDialog(frmDbApp, "Enter order number.");
					} else if (partText.length() < 1) {
						JOptionPane.showMessageDialog(frmDbApp, "Enter part number.");
					} else if (numbText.length() < 1) {
						JOptionPane.showMessageDialog(frmDbApp, "Enter order numbered.");
					} else if (delivText.length() < 1) {
						JOptionPane.showMessageDialog(frmDbApp, "Enter delivery date");
					} else if (delivText.length() == 8){
						int order = Integer.parseInt(orderText);
						int part = Integer.parseInt(partText);
						int numb = Integer.parseInt(numbText);
						int deliv = Integer.parseInt(delivText);
						if (Driver.checkValidPart(part)) {
							Driver.createNewPartOrder(order, part, numb, deliv);
							textField_14.setText("");
							textField_15.setText("");
							textField_16.setText("");
							textField_17.setText("");
						} else {
							JOptionPane.showMessageDialog(frmDbApp, "Invalid part number.");
						}
					} else {
						JOptionPane.showMessageDialog(frmDbApp, "Delivery date must be in YYYYMMDD format.");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(frmDbApp, "An error occured, please check your input.");
				}
			}
		});
		panel_6.add(btnAddOrder, "4, 12");
		
		TableColumn col1 = new TableColumn();
		col1.setIdentifier("this");
		
		JMenuBar menuBar = new JMenuBar();
		frmDbApp.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("file");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent event) {
	            System.exit(0);
	        }
	    });
		mnFile.add(mntmExit);
	
	}

}
